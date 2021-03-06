/**
 * 
 */
package com.gint.app.bisis4.client.libenv;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.librarian.Librarian;


/**
 * @author dimicb
 *
 */

public class LibrarianTableModel extends AbstractTableModel {

	private List<Librarian> libList = new ArrayList<Librarian>();
	private String[] columns;
	
	private String[] defaultPrefixes = {"AU","TI","PU","PY","KW"}; 
	
	public LibrarianTableModel() {
		super();
		libList = LibEnvProxy.getAllLibrarians();
		if(libList!=null){
			columns = new String[5];
			columns[0]=Messages.getString("LibrarianEnvironment.USERNAME");
			columns[1]=Messages.getString("LibrarianEnvironment.FIRST_NAME");
			columns[2]=Messages.getString("LibrarianEnvironment.LAST_NAME");
			columns[3]=Messages.getString("LibrarianEnvironment.E-MAIL");
			columns[4]=Messages.getString("LibrarianEnvironment.ROLES");
		}else{
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Greska u komunikciji sa bazom podataka!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
		
	}
	
	public void initializeModel(){
		libList = LibEnvProxy.getAllLibrarians();	
		if(libList!=null)
			fireTableDataChanged();
		else
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Greska u komunikciji sa bazom podataka!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}

	public int getColumnCount() {
		return columns.length;
	}

	
	public int getRowCount() {		
		return libList.size();
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Librarian lib = libList.get(rowIndex);
		switch(columnIndex){
		case 0: return lib.getUsername();
		case 1: return lib.getIme();
		case 2: return lib.getPrezime();
		case 3: return lib.getEmail();
		case 4: return getUlogeString(lib);		
		}
		return null;
	}
	
	public String getColumnName(int column){
		return columns[column];
	}	
	
	public Librarian getRow(int rowIndex){
		return libList.get(rowIndex);
	}
	
	public boolean updateLibrarian(Librarian lib){
		boolean updateSuccesful = false;
		if(lib.getContext().getPref1()==null)
			setDefaultPrefixes(lib);
		int index = -1;
		for(int i=0;i<libList.size();i++){			
			if(libList.get(i).getUsername().equals(lib.getUsername())){
				index = i;
				break;
			}
		}
		if(index==-1){
			//insert
			libList.add(lib);
			updateSuccesful = LibEnvProxy.addLibrarian(lib);
		}else{
			//update
			libList.set(index, lib);
			updateSuccesful = LibEnvProxy.updateLibrarian(lib);
		}
		if(updateSuccesful)
			fireTableDataChanged();
		return updateSuccesful;
	}
	
	public boolean deleteLibrarian(int index){
		Librarian lib = libList.get(index);
		libList.remove(index);
		fireTableDataChanged();
		return LibEnvProxy.deleteLibrarian(lib);		
	}
	
	public boolean deleteLibrarian(Librarian lib){	
		boolean deleted = LibEnvProxy.deleteLibrarian(lib);
		if(deleted){
			libList.remove(lib);
			fireTableDataChanged();
		}
		return deleted ;		
	}
	
	private String getUlogeString(Librarian lib) {
		StringBuffer ret = new StringBuffer();
		if(lib.isAdministration())			
			ret.append("A");
		if(lib.isCataloguing()){
			if(!ret.toString().equals("")) ret.append(",");
			ret.append("O");
		}
		if(lib.isCirculation()){
			if(!ret.toString().equals("")) ret.append(",");
			ret.append("C");
		}	
		return ret.toString();		
	}
	
	private void setDefaultPrefixes(Librarian lib){
		lib.getContext().setPref1(defaultPrefixes[0]);
		lib.getContext().setPref2(defaultPrefixes[1]);
		lib.getContext().setPref3(defaultPrefixes[2]);
		lib.getContext().setPref4(defaultPrefixes[3]);
		lib.getContext().setPref5(defaultPrefixes[4]);
	}
	
	
	
	
}
