package com.gint.app.bisis4.reports.bgb;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.reports.Report;


public class StatistikaPoInventarnimKnjigama extends Report {
  
	@Override
	public void init() {
		 nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
			log.info("Report initialized.");
	}
	
	@Override
	public void finish() {
		log.info("Finishing report...");

		for (List<Item> list : itemMap.values())
			Collections.sort(list);

		for (String key : itemMap.keySet()) {
			List<Item> list = itemMap.get(key);
			PrintWriter out = getWriter(key);
			
			for (Item i : list) {
				out.print(i.toString());
			}
			out.println("</report>");
			out.close();	
		}
		itemMap.clear();
		log.info("Report finished.");
		
	}
  
   public void handleRecord(Record rec) {
	Map<String, List<String>> oznakeMap = new HashMap<String, List<String>>();
	Map<String, Boolean> recMap = new HashMap<String, Boolean>();
    if (rec == null)
      return;
    String invBr;
    Date dateInv;
    for (Primerak p : rec.getPrimerci()) {
        dateInv=p.getDatumInventarisanja();
        invBr=p.getInvBroj();
        String invKnjiga = invBr.trim().substring(2, 4);
        String key = settings.getParam("file") + getFilenameSuffix(dateInv);
        Item i = getItem(getList(key),invKnjiga);
         if (i == null ){
        	i = new Item(invKnjiga);
        	 List <String>l=oznakeMap.get(key);
        	 if(l==null){
        		 l=new ArrayList<String>();
        		 l.add(invKnjiga);
        		 oznakeMap.put(key, l);
        		 i.add(1, 1);
        	 }else{
        		if (!l.contains(invKnjiga)){
        		    l.add(invKnjiga);
        		    oznakeMap.put(key, l);
        		    i.add(1, 1);
        	    }else{
        	    	 i.add(0, 1);
        	    }
              }
              getList(key).add(i);
        }else{
              List <String>l=oznakeMap.get(key);
         	 if(l==null){
         		 l=new ArrayList<String>();
         		 l.add(invKnjiga);
         		 oznakeMap.put(key, l);
         		 i.add(1, 1);
         	 }else{
         		if (!l.contains(invKnjiga)){
         		    l.add(invKnjiga);
         		    oznakeMap.put(key, l);
         		    i.add(1, 1);
         	    }else{
         	    	 i.add(0, 1);
         	    }
              }
        }
         Item ukupno = getItem(getList(key),"UKUPNO");     
         if (ukupno == null ){
             ukupno = new Item("UKUPNO");
         	Boolean recAdded=recMap.get(key);
         	if(recAdded==null){
         	  recMap.put(key, new Boolean(true));
         	  ukupno.add(1, 1);
         	}else{
         		ukupno.add(0, 1);
         	}
         	getList(key).add(ukupno); 
         }else{
         	Boolean recAdded=recMap.get(key);
         	if(recAdded==null){
         	  recMap.put(key, new Boolean(true));
         	  ukupno.add(1, 1);
         	}else{
         		ukupno.add(0, 1);
         	}
             
         }

    }
  }
   
   public List<Item> getList(String key) {
	    List<Item> list = itemMap.get(key);
	    if (list == null) {
	      list = new ArrayList<Item>();
	      itemMap.put(key, list);
	    }
	    return list;
	  }

 private Item getItem(List<Item> il,String invKnjiga) {
		for (Item i : il){		
			if (i.inv.compareToIgnoreCase(invKnjiga)==0){	
				return i;
			}
		}
	    return null;        
 }

  public String nvl(String s) {
    return s == null ? "" : s;
  }

  public class Item implements Comparable {
    public String inv;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public Item(String inv) {
      super();
      this.inv = inv;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return inv.compareTo(b.inv);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item  total=\"");
      buf.append(isTotal);
      buf.append("\">\n    <inv>");
      buf.append(getName(inv));
      buf.append("</inv>\n    <brNas>");
      buf.append(brNas);
      buf.append("</brNas>\n   <brPr>");
      buf.append(brPr);
      buf.append("</brPr>\n    </item>");
      return buf.toString();
    }

    public void add(int brNas, int brPr) {
      this.brNas += brNas;
      this.brPr += brPr;
    }

    public void addItem(Item i) {
      brNas += i.brNas;
      brPr += i.brPr;
    }
  }

  public String getName(String code) {
    if (code == null || code.equals(""))
      return "nerazvrstano";
    if (code.equals("00"))
      return "X";
    if (code.equals("20"))
      return "B";
    if (code.equals("30"))
      return "D";
    if (code.equals("60"))
      return "L";
    if(code.equalsIgnoreCase("UKUPNO"))
      return "UKUPNO";
    else
      return code + " - nerazvrstano";
  }





  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  int recordSum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(StatistikaPoInventarnimKnjigama.class);
  NumberFormat nf;


@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}

@Override
public void finishInv() {
	// TODO Auto-generated method stub
	
}


}
