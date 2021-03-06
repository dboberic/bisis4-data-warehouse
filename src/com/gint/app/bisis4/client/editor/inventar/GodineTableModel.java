package com.gint.app.bisis4.client.editor.inventar;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.recordtree.CurrRecord;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Sveska;
import com.gint.app.bisis4.utils.Signature;

public class GodineTableModel extends AbstractTableModel {
  
  private String[] godineAll;
  private String[] columns;
  private String[] columnSet;
  
  private static Log log = LogFactory.getLog(GodineTableModel.class.getName());
  
  public GodineTableModel(){
    super();
    godineAll = new String[17];
    godineAll[0] = "Inventarni broj";
    godineAll[1] = "Datum inventarisanja";
    godineAll[2] = "Signatura";
    godineAll[3] = "Odeljenje";
    godineAll[4] = "Broj ra\u010duna";
    godineAll[5] = "Datum ra\u010duna";
    godineAll[6] = "Cena";
    godineAll[7] = "Dobavlja\u010d";
    godineAll[8] = "Finansijer";
    godineAll[9] = "Povez";
    godineAll[10] = "Na\u010din nabavke";
    godineAll[11] = "Napomene";
    godineAll[12] = "Godi\u0161te";
    godineAll[13] = "Godina";
    godineAll[14] = "Broj";
    godineAll[15] = "Dostupnost";
    godineAll[16] = "Inventator";    
    String columnSetStr = BisisApp.getINIFile().getString("cataloguing", "godineModel");
    columnSet = columnSetStr.split(" ");
    columns = new String[columnSet.length];
    for(int i=0;i<columnSet.length;i++){
      columns[i] = godineAll[Integer.parseInt(columnSet[i])];      
    }   
  }  

  public int getColumnCount() {   
    return columns.length;
  }
  
  public String getColumnName(int column){
    return columns[column];
  }

  public int getRowCount() {   
    return CurrRecord.brojGodina();
  }
  
  public Godina getRow(int rowIndex){
    return CurrRecord.getGodina(rowIndex);
    
  }

  public Object getValueAt(int rowIndex, int columnIndex) {  
    Godina g = CurrRecord.getGodina(rowIndex);
    return getValueForColumn(g,Integer.parseInt(columnSet[columnIndex]));    
  }
  
  public Object getValueForColumn(Godina g, int columnIndex) {   
    try{      
      if(g!=null){
        switch(columnIndex){
        case 0: return g.getInvBroj(); 
        case 1: if(g.getDatumInventarisanja()!=null)
              return InventarConstraints.sdf.format(g.getDatumInventarisanja());
            else return "";        
        case 2: return Signature.format(g);     
        case 3: return g.getOdeljenje();
        case 4: return g.getBrojRacuna();     
        case 5: if(g.getDatumRacuna()!=null)
              return InventarConstraints.sdf.format(g.getDatumRacuna());
            else
              return "";    
        case 6:  return g.getCena();
        case 7:  return g.getDobavljac();
        case 8:  return g.getFinansijer();
        case 9:  return g.getPovez();
        case 10: return g.getNacinNabavke();
        case 11: return g.getNapomene();
        case 12: return g.getGodiste();
        case 13: return g.getGodina();
        case 14: return g.getBroj();
        case 15: return g.getDostupnost();
        case 16: return g.getInventator();
        }     
      }
    }catch(Exception ex){
      log.fatal(ex);
    }
    return null;
  }
  
  public void initializeModel(){
    this.fireTableDataChanged();
  }
  
  
  public void updateGodina(Godina g, boolean changeInvBr) throws InventarniBrojException{
//  identifikacija godina se vrsi preko inventarnog broja
    // ako nema inventarnog broja generise se nova godina
    String invBroj = g.getInvBroj();   
    boolean found = false;
    int index = -1;
    int i = 0;
    boolean hasInvBroj = g.getInvBroj()==null || !g.getInvBroj().endsWith("##");
    if(hasInvBroj){
      while(!found && i<CurrRecord.brojGodina()){
        if(CurrRecord.getGodina(i).getInvBroj()!=null && CurrRecord.getGodina(i).getInvBroj().equals(invBroj)){
          found = true;
          index = i;
        }else i++;
      }
      if(found){
      	if(changeInvBr && !InventarValidation.validateInvBrojUnique(g.getInvBroj()).equals("")){
        throw new InventarniBrojException(InventarValidation.validateInvBrojUnique(g.getInvBroj()));
      	}
      	 g.setGodinaID(CurrRecord.record.getGodine().get(index).getGodinaID());
        CurrRecord.record.getGodine().set(index,g);
      }else{
        // provera jedinstvenosti inventarnog broja
        if(!InventarValidation.validateInvBrojUnique(g.getInvBroj()).equals(""))
          throw new InventarniBrojException(InventarValidation.validateInvBrojUnique(g.getInvBroj()));
        for(Sveska s:g.getSveske()){
        	if(!InventarValidation.validateInvBrojUnique(s.getInvBroj()).equals(""))
        		throw new InventarniBrojException("Godina ne mo\u017ee biti sa\u010duvana" +
        				"\n Postoje\u0107i inventarni broj sveske!");        	
        }
        CurrRecord.addGodina(g);
      }      
    }else{
      throw new InventarniBrojException("Morate uneti inventarni broj!");
    }
    fireTableDataChanged();
  }  
  
  /*
   * 9.9.2008. Za napomene i inventatora se dodaje vrednost
   */
  public void updateGodine(int[]rows, Godina g){
    for(int i=0;i<rows.length;i++){
      Godina currg = CurrRecord.getGodina(rows[i]);
      if(g.getNacinNabavke()!=null) currg.setNacinNabavke(g.getNacinNabavke());
      if(g.getPovez()!=null) currg.setPovez(g.getPovez());     
      if(g.getBrojRacuna()!=null) currg.setBrojRacuna(g.getBrojRacuna());
      if(g.getDatumRacuna()!=null) currg.setDatumRacuna(g.getDatumRacuna());
      if(g.getFinansijer()!=null) currg.setFinansijer(g.getFinansijer());
      if(g.getDobavljac()!=null) currg.setDobavljac(g.getDobavljac());
      if(g.getCena()!=null) currg.setCena(g.getCena());
      if(g.getSigUDK()!=null) currg.setSigUDK(g.getSigUDK());
      if(g.getSigPodlokacija()!=null) currg.setSigPodlokacija(g.getSigPodlokacija());
      if(g.getSigFormat()!=null) currg.setSigFormat(g.getSigFormat());
      if(g.getSigIntOznaka()!=null) currg.setSigIntOznaka(g.getSigIntOznaka());
      if(g.getSigDublet()!=null) currg.setSigDublet(g.getSigDublet());
      if(g.getSigNumerusCurens()!=null) currg.setSigNumerusCurens(g.getSigNumerusCurens());
      if(g.getDatumInventarisanja()!=null) currg.setDatumInventarisanja(g.getDatumInventarisanja());   
      if(g.getNapomene()!=null){
      	if(currg.getNapomene()==null)      		
      		currg.setNapomene(g.getNapomene());
      	else
      		currg.setNapomene(currg.getNapomene()+g.getNapomene());
      }
      if(g.getGodina()!=null) currg.setGodina(g.getGodina());
      if(g.getGodiste()!=null) currg.setGodiste(g.getGodiste());
      if(g.getBroj()!=null) currg.setBroj(g.getBroj());
      if(g.getDostupnost()!=null) currg.setDostupnost(g.getDostupnost());
      if(g.getInventator()!=null){
      	if(currg.getInventator()==null)
      		currg.setInventator(g.getInventator());
      	else
      		currg.setInventator(currg.getInventator()+g.getInventator());
      }
    }
    this.fireTableDataChanged();
  }
  
  public void deleteRow(int row){
    CurrRecord.removeGodina(row);
    fireTableDataChanged();
  }
  
  public int getColumnIndex(String columnName){ 		
 		for(int i=0;i<columns.length;i++){				
 				if(columns[i].equals(columnName)) return i;
 			} 		
 		return -1;			
 	}
 	
 	public boolean isSifriranaKolona(int colIndex){
 		return
 			colIndex == getColumnIndex("Status") ||
 			colIndex == getColumnIndex("Odeljenje") ||
 			colIndex == getColumnIndex("Na\u010din nabavke") ||
 			colIndex == getColumnIndex("Povez") ||
 			colIndex == getColumnIndex("Dostupnost");
 			
 	}
}
