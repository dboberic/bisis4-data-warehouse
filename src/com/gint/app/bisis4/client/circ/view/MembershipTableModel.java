package com.gint.app.bisis4.client.circ.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.model.MmbrTypes;
import com.gint.app.bisis4.client.circ.model.Signing;
import com.gint.app.bisis4.client.circ.model.UserCategs;

public class MembershipTableModel extends AbstractTableModel implements Serializable {


    protected List<Signing>  dataView;
    protected List<String>  columnIdentifiers;

    public MembershipTableModel() {
    	columnIdentifiers = new ArrayList<String>();
    	columnIdentifiers.add(Messages.getString("circulation.location")); //$NON-NLS-1$
    	columnIdentifiers.add(Messages.getString("circulation.begindate")); //$NON-NLS-1$
    	columnIdentifiers.add(Messages.getString("circulation.expirdate")); //$NON-NLS-1$
    	columnIdentifiers.add(Messages.getString("circulation.cost")); //$NON-NLS-1$
    	columnIdentifiers.add(Messages.getString("circulation.receipt")); //$NON-NLS-1$
    	dataView = new ArrayList<Signing>();
    }
    
    public void setData(Set data){
      this.dataView.clear();
      Iterator it = data.iterator();
      while (it.hasNext()){
        this.dataView.add((Signing)it.next());
      }
    	fireTableDataChanged();
    }
    
//		 Manipulating rows
    
	    public void addRow(Location loc) {
	    	int row = getRowCount();
	    	Signing rowData = new Signing();
	    	rowData.setLibrarian(Cirkulacija.getApp().getLibrarian().getUsername());
	    	rowData.setSignDate(new Date());
        rowData.setLocation(loc);
        Cirkulacija.getApp().getUserManager().addSigning(rowData);
        this.dataView.add(rowData);
	    	fireTableRowsInserted(row, row);
	    }


	    public void removeRow(int row) {
	      Signing sig = dataView.remove(row);    
        Cirkulacija.getApp().getUserManager().removeSigning(sig);
	      fireTableRowsDeleted(row, row);
	    }


		//
//		 Implementing the TableModel interface
		//

	    public int getRowCount() {
	        return dataView.size();
	    }

	    public int getColumnCount() {
	        return columnIdentifiers.size();
	    }

	    public String getColumnName(int column) {
	        Object id = null; 
		// This test is to cover the case when 
		// getColumnCount has been subclassed by mistake ... 
		if (column < columnIdentifiers.size()) {  
		    id = columnIdentifiers.get(column); 
		}
	        return (id == null) ? super.getColumnName(column) 
	                            : id.toString();
	    }
      
      public void setColumnName(int column, String text) {
        if (column < columnIdentifiers.size()) {  
          columnIdentifiers.set(column, text); 
          fireTableStructureChanged();
        }
      }

	    public boolean isCellEditable(int row, int column) {
	        return true;
	    }

	    public Object getValueAt(int row, int column) {
	        Signing rowData = dataView.get(row);
	        switch (column){
	        	case 0: return rowData.getLocation();
	        	case 1: return rowData.getSignDate();
	        	case 2: return rowData.getUntilDate();
	        	case 3: return rowData.getCost();
	        	case 4: return rowData.getReceiptId();
	        	default: return null;
			}
	    }

	    public void setValueAt(Object aValue, int row, int column) {
	        Signing rowData = dataView.get(row);
	        switch (column){
	        	case 0: rowData.setLocation((Location)aValue);
	        			break;
	        	case 1: rowData.setSignDate((Date)aValue);
	        			break;
	        	case 2: rowData.setUntilDate((Date)aValue);
	        			break;
	        	case 3: rowData.setCost((Double)aValue);
	        			break;
	        	case 4: rowData.setReceiptId((String)aValue);
	        }
	        fireTableCellUpdated(row, column);
	        Cirkulacija.getApp().getUserManager().updateSigning(rowData);
	    }

	    public Class getColumnClass(int col) {
			switch (col){
			 case 0: return Location.class;
			 case 1: return Date.class;
			 case 2: return Date.class;
			 case 3: return Double.class;
			 case 4: return String.class;
			 default: return String.class;
			}
	    }
      
      public void computeMmbrship(MmbrTypes mt, UserCategs uc){
          Double cost = Cirkulacija.getApp().getUserManager().getMembership(mt, uc);
          setValueAt(cost, getRowCount()-1, 3);
      }
      
      public void computePeriod(Integer period){
        Date startDate = (Date)getValueAt(getRowCount()-1, 1);
        Calendar cl = Calendar.getInstance();
        cl.setTime(startDate);
        cl.add(Calendar.DATE, period);
        setValueAt(cl.getTime(), getRowCount()-1, 2);
      }
}
