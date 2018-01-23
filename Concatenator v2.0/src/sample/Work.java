package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sample.controllers.ControllerModalWindow;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public class Work {
private String filePath1;
private String filePath2;
public Button b4;
public Label lb3;
public TextArea txtA1;
public TextArea txtA2;


     public Work(String filePath1,String filePath2 ) {
         this.filePath1 = filePath1;
         this.filePath2 = filePath2;
     }
// Получаем директорию для выходного файла
    public  String getOutPutFileName(){
        String outPutFileName = filePath1.substring(filePath1.lastIndexOf("\\"), filePath1.length()); //получаем имя выходного файла
        outPutFileName = ((outPutFileName.replace(".txt","_Out"))+".txt");              //дбавляем слово out к файлу

        return System.getProperty("user.dir").concat(outPutFileName);  //определяем из какой папки была запушена программа
    }
// Получаем шапку таблицы с данными
    public  String[] getShapka() throws IOException {
        String[] shapka = {};
        BufferedReader shapkaReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath1), "windows-1251"));

        String s1 = shapkaReader.readLine();
        shapka = s1.split("[\t]");
        shapkaReader.close();

        return shapka;
    }
// Заполняем данными list1
    public  List<RowObj> getList1() throws IOException {

        List<RowObj> list1 = new ArrayList<RowObj>();
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath1), "windows-1251"));
        String[] s1Mass;
        int countRowList1 = 1;

        while (reader1.ready()) {
            String s1 = reader1.readLine();
            if (countRowList1 == 1) {
                countRowList1++;
            } else {
                s1Mass = s1.split("[\t]");
                double salesV = 0;
                double salesI = 0;

// Нужно обработать ситуацию с разрывом внутри строки

                RowObj obj = new RowObj("0", "0",
                        "0", "0", "0", "0", "0", "0", "0", 0, 0, "0", "0");
                try {
                    obj = new RowObj(s1Mass[0], s1Mass[1], s1Mass[2], s1Mass[3], s1Mass[4],
                            s1Mass[5], s1Mass[6], s1Mass[7], s1Mass[8], salesI, salesV, s1Mass[11], s1Mass[12]);
// В файле в качестве разделителя дробной части присутствект , а не .
                    salesValueAndsalesItem(s1Mass, countRowList1, s1);
                } catch (ArrayIndexOutOfBoundsException e) {


                    String nextLine = reader1.readLine();
                    String[] massNextLine = nextLine.split("[\t]");
                    List<String> strList1 = new ArrayList<String>(Arrays.asList(massNextLine));
//                  strList.removeIf(i -> i.equals(""));

                    Iterator<String> iter = strList1.iterator();
                    while (iter.hasNext()) {
                        String s = iter.next();
                        if (s.equals("")) {
                            iter.remove();
                        }
                    }

                    String restoreString = s1 + nextLine;
                    String[] massRestoreString = restoreString.split("[\t]");

                    if (massNextLine.length == 13) {
                        obj = new RowObj(massNextLine[0], massNextLine[1], massNextLine[2], massNextLine[3], massNextLine[4],
                                massNextLine[5], massNextLine[6], massNextLine[7], massNextLine[8], salesI, salesV, massNextLine[11], massNextLine[12]);
                    } else if (strList1.size() + s1Mass.length == massRestoreString.length) {
                            obj = new RowObj(massRestoreString[0], massRestoreString[1], massRestoreString[2], massRestoreString[3], massRestoreString[4],
                                    massRestoreString[5], massRestoreString[6], massRestoreString[7], massRestoreString[8], salesI, salesV, massRestoreString[11], massRestoreString[12]);
                            lineBreak(massRestoreString, countRowList1, s1, nextLine);
                            salesValueAndsalesItem(massRestoreString, countRowList1, s1);
                        } else {
                           // lineBreak(massRestoreString, countRowList1, s1, nextLine);
                        }

                }

// Проверяем дубликаты, если они есть агрегируем
                if (list1.indexOf(obj) != -1) {
                    int list1Index = list1.indexOf(obj);
                    obj.salesvalues = obj.salesvalues + list1.get(list1Index).salesvalues;
                    obj.salesitems = obj.salesitems + list1.get(list1Index).salesitems;
                    list1.set(list1Index, obj);

                } else {
                    list1.add(obj);
                }
            }
            countRowList1++;
        }
        reader1.close();
        return list1;
    }


// Заполняем данными list2
    public List<RowObj> getList2() throws IOException {
        List<RowObj> list2 = new ArrayList<RowObj>();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath2), "windows-1251"));
        String[] s2Mass;
        int countRowList2 = 1;

        while (reader2.ready()) {
            String s2 = reader2.readLine();
            if (countRowList2 == 1) {

                countRowList2++;
            } else {
                s2Mass = s2.split("[\t]");
                double salesV = 0;
                double salesI = 0;
// В файле в качестве разделителя дробной части присутствект , а не .
                    try {
                        salesV = Double.parseDouble((s2Mass[9].replace(",", ".")));
                    } catch (Exception e) {

                         salesValue(s2Mass,countRowList2,s2);
                    }

                    try {
                        salesI = Double.parseDouble((s2Mass[10].replace(",", ".")));
                    } catch (Exception e) {

                        salesItem(s2Mass,countRowList2,s2);
                    }


                    RowObj obj = new RowObj(s2Mass[0], s2Mass[1], s2Mass[2], s2Mass[3], s2Mass[4],
                            s2Mass[5], s2Mass[6], s2Mass[7], s2Mass[8], salesI, salesV, s2Mass[11], s2Mass[12]);

                    //проверяем дубликаты, если они есть агрегируем
                    if (list2.indexOf(obj) != -1) {
                        int list2Index = list2.indexOf(obj);
                        obj.salesvalues = obj.salesvalues + list2.get(list2Index).salesvalues;
                        obj.salesitems = obj.salesitems + list2.get(list2Index).salesitems;
                        list2.set(list2Index, obj);

                    } else {
                        list2.add(obj);
                    }
                }
            countRowList2++;
            }
        reader2.close();
        return list2;
    }

// Заполняем данными result
    public List<RowObj> getResultList(List<RowObj> list1, List<RowObj> list2) throws IOException {
        List<RowObj> result = new ArrayList<RowObj>();
        StackTraceElement[] massForStackTrace = {};
        String strStackTraceElement;

        for (RowObj itemList1 : list1) {
            for (RowObj itemList2 : list2) {

                if ((list2.contains(itemList1)) && ((!result.contains(itemList1)))) {                 //не работает indexOF
                    itemList1.salesvalues = itemList1.salesvalues + itemList2.salesvalues;
                    itemList1.salesitems = itemList1.salesitems + itemList2.salesitems;
                    result.add(itemList1);

                } else if ((!list2.contains(itemList1)) && ((!result.contains(itemList1)) && ((!result.contains(itemList2))))) {
                    result.add(itemList1);
                    result.add(itemList2);

                } else if ((!list2.contains(itemList1)) && ((result.contains(itemList1)) && ((!result.contains(itemList2))))) {
                    result.add(itemList2);

                } else if ((!list2.contains(itemList1)) && ((!result.contains(itemList1)) && ((result.contains(itemList2))))) {
                    result.add(itemList1);

                } else if ((list2.contains(itemList1)) && ((result.contains(itemList1)) && ((!result.contains(itemList2))))) {
                    result.add(itemList2);

                } else if ((list2.contains(itemList1)) && ((result.contains(itemList1)) && (itemList1.equals(itemList2)))) {
                    int index = result.indexOf(itemList1);
                    itemList1.salesvalues = itemList1.salesvalues + itemList2.salesvalues;
                    itemList1.salesitems = itemList1.salesitems + itemList2.salesitems;
                    result.set(index, itemList1);
                }

            }

        }

        return result;

    }
// Сборка строк с ошибками и передача их в Модальное окно
public void strErrorBuilder(String str){
    b4.setVisible(true);
   // Thread.currentThread().getStackTrace()[1].getClassName();
    StackTraceElement[] s = Thread.currentThread().getStackTrace();
    String st = s[3].toString()+" "+s[4].toString();
    if(st.contains("getList1")) {
        txtA1.setText(txtA1.getText() + str);

    } else if(st.contains("getList2")){
        txtA2.setText(txtA2.getText() + str);

    }
}

// Пишим в файл
public void writeOutPutFile(String currentdir,String[] shapka, List<RowObj> result ) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(currentdir));

        for (int i = 0; i < shapka.length; i++) {
            writer.write(shapka[i] + '\t');
        }
        writer.newLine();

        for (RowObj item : result) {
            writer.write(item.toString());
            writer.newLine();
        }
        writer.close();

    lb3.setText("Файл сгенерирован");

    }

    private void salesValueAndsalesItem(String[] s1Mass, int countRowList1, String s1) throws IOException {
        double salesV;
        double salesI;
        try {
            salesV = Double.parseDouble((s1Mass[9].replace(",", ".")));

        } catch (NumberFormatException e) {

            salesValue(s1Mass, countRowList1, s1);

        }

        try {
            salesI = Double.parseDouble((s1Mass[10].replace(",", ".")));

        } catch (NumberFormatException e) {

            salesItem(s1Mass, countRowList1, s1);

        }
    }

    private void salesItem(String[] s1Mass, int countRowList1, String s1) throws IOException {
        StringBuilder introRow = new StringBuilder("Ошибка в строке " +(countRowList1-1)+ ": нет значения в поле salesitem" +'\n');
        StringBuilder shapkaRow = new StringBuilder(Arrays.toString(getShapka())+'\n');
        StringBuilder s1Row = new StringBuilder(s1+'\n');
        StringBuilder restoreRowText = new StringBuilder("Строка была исправлена на строку:"+'\n');
        s1Mass[10] = "0";
        StringBuilder restoreRow = new StringBuilder(Arrays.toString(s1Mass)+'\n');
        StringBuilder separatorRow = new StringBuilder("-----------------------------------------------------------------------------------------------------------------------------------------------"+'\n');
        strErrorBuilder(introRow.append(shapkaRow).append(s1Row).append(restoreRowText).append(restoreRow).append(separatorRow).toString());
    }

    private void salesValue(String[] s1Mass, int countRowList1, String s1) throws IOException {
        StringBuilder introRow = new StringBuilder("Ошибка в строке " +(countRowList1-1)+ ": нет значения в поле salesvalue" +'\n');
        StringBuilder shapkaRow = new StringBuilder(Arrays.toString(getShapka())+'\n');
        StringBuilder s1Row = new StringBuilder(s1+'\n');
        StringBuilder restoreRowText = new StringBuilder("Строка была исправлена на строку:"+'\n');
        s1Mass[9] = "0";
        StringBuilder restoreRow = new StringBuilder(Arrays.toString(s1Mass)+'\n');
        StringBuilder separatorRow = new StringBuilder("-----------------------------------------------------------------------------------------------------------------------------------------------"+'\n');
        strErrorBuilder(introRow.append(shapkaRow).append(s1Row).append(restoreRowText).append(restoreRow).append(separatorRow).toString());
    }

    private void lineBreak(String[] s1Mass, int countRowList1, String s1,String s2) throws IOException {
        StringBuilder introRow = new StringBuilder("Ошибка в строке " +(countRowList1-2)+" и в строке "+(countRowList1-1)+ ": разрыв в строке" +'\n');
        StringBuilder shapkaRow = new StringBuilder(Arrays.toString(getShapka())+'\n');
        StringBuilder s1Row1 = new StringBuilder(s1+'\n');
        StringBuilder s1Row2 = new StringBuilder(s2+'\n');
        StringBuilder restoreRowText = new StringBuilder("Строки "+(countRowList1-2)+" и "+(countRowList1-1)+ " были исправлена на строку:"+'\n');
        StringBuilder restoreRow = new StringBuilder(Arrays.toString(s1Mass)+'\n');
        StringBuilder separatorRow = new StringBuilder("-----------------------------------------------------------------------------------------------------------------------------------------------"+'\n');
        strErrorBuilder(introRow.append(shapkaRow).append(s1Row1).append(s1Row2).append(restoreRowText).append(restoreRow).append(separatorRow).toString());
    }
}


