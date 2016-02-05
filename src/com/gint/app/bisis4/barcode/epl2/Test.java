package com.gint.app.bisis4.barcode.epl2;

import java.math.BigInteger;

public class Test {
  public static void main(String[] args) throws Exception {
    //IPrinter printer = new RemotePrinter("localhost", "7000");
    IPrinter printer = Printer2.getInstance();
    Label label = new Label(300, 0, 203, 2, 2, 10, "1250");
  /*  label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.163.41.09");
    label.appendCode128("K01000140010");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.163.41-1");
    label.appendCode128("K27000140011");
    printer.print(label);*/

    //label = new Label(0, 0, 203,3,2,50,"1250");
    label.appendText("Test Test",3);
    label.appendBlankLine();
    label.appendCode128("K00000001540");
    printer.print(label,"1250");
    System.out.println(label.getCommands());
    System.out.println(String.format("%x", 
        new BigInteger(label.getCommands().getBytes("cp1251"))));

 /*   label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("94(497.1)");
    label.appendCode128("K01000140015");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("94(497.1)");
    label.appendCode128("K23000140016");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K01000139628");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K01000139629");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K01000139630");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K05000139631");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K19000139632");
    printer.print(label);*/
  }
}
