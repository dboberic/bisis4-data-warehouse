package com.gint.app.bisis4.client.editor.inventar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.editor.Obrada;

public class InventarniBrojPanel extends JPanel {
	
	private JTextField odeljenjeTxtFld;
	private JTextField invKnjTxtFld;
	private JTextField brojTxtFld;
	private JButton button;
	private boolean changeInvBr = false; //dodato zbog provere duplih inventarnih brojeva pri promeni postojeceg invetarnog broja
	
	  
  private static Log log = LogFactory.getLog(InventarniBrojPanel.class.getName());

	

	public InventarniBrojPanel() {	
		odeljenjeTxtFld = new JTextField(2);
		//odeljenjeTxtFld.setEditable(false);
		//odeljenjeTxtFld.setFocusable(false);
		invKnjTxtFld = new JTextField(2);
		//invKnjTxtFld.setEditable(false);
		//invKnjTxtFld.setFocusable(false);
		brojTxtFld = new JTextField(15);
		button = new JButton(new ImageIcon(RaspodelaFrame.class
				.getResource("/com/gint/app/bisis4/client/images/Check16.png")));
		button.setFocusable(false);
		button.setToolTipText("Genirisanje inventarnog broja");
		if(InventarConstraints.startPos==0 && InventarConstraints.endPos==0)
			button.setEnabled(false);
		else
			button.setEnabled(true);
		MigLayout layout = new MigLayout("insets 0 0 0 0","","[b][b]");
		setLayout(layout);
		add(odeljenjeTxtFld,"grow");
		add(invKnjTxtFld,"grow");
		add(brojTxtFld,"grow");
		add(button);
		// actions
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleGenerate();				
			}			
		});    
    button.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent ke) {
        handleKeys(ke);        
      }     
    });    
    brojTxtFld.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent ke) {
    	  changeInvBr = true;
        handleKeys(ke);        
      }     
    });    
	}	

	protected void handleKeys(KeyEvent ke) {
    if(ke.getKeyCode()==KeyEvent.VK_ENTER){
    	if(brojTxtFld.getText().equals(""))
    		button.doClick();
    	else
    		loadInvBroj();
    		
    }    
  }
	
  private void loadInvBroj(){
	  generate();
	  Obrada.editorFrame.getInventarPanel().loadItem();
  }

  public String getInventarniBroj(){
   /* String message = "";  
    if(brojTxtFld.getText().equals(""))
      message = generate();    
		if(message.equals("")){*/
			StringBuffer broj = new StringBuffer();
			broj.append(odeljenjeTxtFld.getText());
			broj.append(invKnjTxtFld.getText());		
			broj.append(brojTxtFld.getText());      
			return broj.toString(); 
	}
	
	public void setInventarniBroj(String str){
		if(!str.equals("") && str.length()>3){	     
	   odeljenjeTxtFld.setText(str.substring(0,2));
	   invKnjTxtFld.setText(str.substring(2,4));
	   brojTxtFld.setText(str.substring(4));              
	 }else{
	  	odeljenjeTxtFld.setText("");
    invKnjTxtFld.setText("");
    brojTxtFld.setText("");  
	  }
	}
	
	public void resetPanel(){
		odeljenjeTxtFld.setText("");
		invKnjTxtFld.setText("");
		brojTxtFld.setText("");
	}
	
	public void setOdeljenje(String odeljenje){
		odeljenjeTxtFld.setText(odeljenje);
	}
	
	public void setInvKnj(String invKnj){
		invKnjTxtFld.setText(invKnj);
		
	}
	
	public String getOdeljenje(){
		return odeljenjeTxtFld.getText();
	}
	
	private String generate(){
		StringBuffer message = new StringBuffer();
		String invBroj;
		int duzinaBroja = InventarConstraints.duzinaInventarnogBroja - 4;    
		String nule = "000000000".substring(0,duzinaBroja);    
		if((odeljenjeTxtFld.getText().equals("")) || invKnjTxtFld.getText().equals("")){
			message.append("Nisu uneti svi potrebni podaci za generisanje inventarnog broja!\n");			
		}else{
			try{        
        if(brojTxtFld.getText().equals(""))
         dbGenerate();  			          
  			if(brojTxtFld.getText().length()>duzinaBroja) throw new Exception();  			
  			invBroj = nule.substring(brojTxtFld.getText().length())+brojTxtFld.getText();  			
  			brojTxtFld.setText(invBroj);
        if(!checkInvBroj().equals("")){
          message.append(checkInvBroj());          
        }
			}catch(Exception e){
        if(e instanceof InventarniBrojException)
          message.append(e.getMessage());
        else
          message.append("Gre\u0161ka u inventarnom broju!");
				brojTxtFld.setText("");
			}
		}
		return message.toString();
	}
	
	// genirasanje inventarnog broja i provera jedinstvenosti ako se unosi broj	
	private void handleGenerate() {
		String message = generate();
    if(!message.equals("")){
      JOptionPane.showMessageDialog(BisisApp.getMainFrame(),message,"Gre\u0161ka",JOptionPane.ERROR_MESSAGE);
      brojTxtFld.setText("");
    }
	
	}
  
  private void dbGenerate() throws InventarniBrojException{
    String brojacName="";
    if(InventarConstraints.startPos==1 && InventarConstraints.endPos==2)
    	brojacName = odeljenjeTxtFld.getText();
    if(InventarConstraints.startPos==1 && InventarConstraints.endPos==4)
    	brojacName = odeljenjeTxtFld.getText()+invKnjTxtFld.getText();
    if(InventarConstraints.startPos==3 && InventarConstraints.endPos==4)
    	brojacName = invKnjTxtFld.getText();        
    if(!brojacName.equals("")){
    	int broj = BisisApp.getRecordManager().getNewID(brojacName);    	
	    if(broj!=-1){
	      brojTxtFld.setText(""+broj);
	      log.warn("Nije inicijalizovan brojac za generisanje inventarnog broja!");
	    }else throw new InventarniBrojException("Broja\u010d nije inicijalizovan!");
    }
  }
  
  private String getValue(){
    return odeljenjeTxtFld.getText()+invKnjTxtFld.getText()+brojTxtFld.getText();
  }
  
  private String checkInvBroj(){
    String message = "";
    String broj = odeljenjeTxtFld.getText()+invKnjTxtFld.getText()+brojTxtFld.getText();
    if(InventarValidation.isDuplicatedInvBroj(broj))
      message = "Inventarni broj je ve\u0107 zauzet!\n";    
    return message;    
  }  
  
  /*
   * vraca broj bez prefiksa za odeljenje i inventarnu knjigu
   */
  public String getBroj(){
  	String ret = brojTxtFld.getText();
  	while(ret.startsWith("0"))
  		ret = ret.substring(1);
  	return ret;
  }
  
  public void setEnabled(boolean enabled){
    brojTxtFld.setEnabled(enabled);
    button.setEnabled(enabled);    
  }
  
  public synchronized void addKeyListener(KeyListener arg0) {  
    brojTxtFld.addKeyListener(arg0);
  }
  
  public void requestFocus() {
    brojTxtFld.requestFocus();
  }  
  
  public boolean isEnabled() {  
    return brojTxtFld.isEnabled();
  }
  
  	public boolean isChangeInvBr() {
		return changeInvBr;
	}
	
	public void setChangeInvBr(boolean changeInvBr) {
		this.changeInvBr = changeInvBr;
	}
}
