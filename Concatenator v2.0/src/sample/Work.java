package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sample.controllers.ControllerModalWindow;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Work {
private String filePath1;
private String filePath2;
public Button b4;
public Label lb3;
public TextArea txtA1;
public TextArea txtA2;

     private Work() {

     }

     public Work(String filePath1,String filePath2 ) {
         this.filePath1 = filePath1;
         this.filePath2 = filePath2;
     }
//получаем директорию для выходного файла
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
        StackTraceElement[] massForStackTrace;
        String strStackTraceElement;
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

                // в файле в качестве разделителя дробной части присутствект , а не .
                try {
                    salesV = Double.parseDouble((s1Mass[9].replace(",", ".")));

                } catch (NumberFormatException e) {
                    massForStackTrace = e.getStackTrace();
                    strStackTraceElement = massForStackTrace[0].toString();
                    if(strStackTraceElement.contains("empty String")){
                        StringBuilder introRow = new StringBuilder("Ошибка в строке " +countRowList1+ "нет значения в поле salesvalue" +'\n');
                        StringBuilder shapkaRow = new StringBuilder(Arrays.toString(getShapka())+'\n');
                        StringBuilder s1Row = new StringBuilder(s1);
                        StringBuilder restoreRowText = new StringBuilder("Строка была исправлена на строку:"+'\n');
                //        StringBuilder restoreRow = new StringBuilder(obj+'\n');
                        StringBuilder separatorRow = new StringBuilder("----------------------------------------------------------"+'\n');

                    } else if(strStackTraceElement.contains("For input string")){


                    }else {


                    }

                }

                try {
                    salesI = Double.parseDouble((s1Mass[10].replace(",", ".")));

                } catch (NumberFormatException e) {
                    b4.setVisible(true);
                    e.printStackTrace();
                }
                // Нужно обработать ситуацию с разрывом внутри строки

                RowObj obj = new RowObj("0", "0",
                        "0", "0", "0", "0", "0", "0", "0", 0, 0, "0", "0");
                try {
                    obj = new RowObj(s1Mass[0], s1Mass[1], s1Mass[2], s1Mass[3], s1Mass[4],
                            s1Mass[5], s1Mass[6], s1Mass[7], s1Mass[8], salesI, salesV, s1Mass[11], s1Mass[12]);      //не понятен порядок salesvalue и salesitem
                } catch (ArrayIndexOutOfBoundsException e) {
                    b4.setVisible(true);

                    //         controllerModal.errorLogList1(strStackTraceElement);
                    String s2 = reader1.readLine();
                    String[] s2MassNextLine = s2.split("[\t]");
                    if (s2MassNextLine.length == 13) {
                        obj = new RowObj(s2MassNextLine[0], s2MassNextLine[1], s2MassNextLine[2], s2MassNextLine[3], s2MassNextLine[4],
                                s2MassNextLine[5], s2MassNextLine[6], s2MassNextLine[7], s2MassNextLine[8], salesI, salesV, s2MassNextLine[11], s2MassNextLine[12]);
                    } else if (s2MassNextLine.length + s1Mass.length == 13) {
                        String restoreString = s1 + s2;
                        String[] massRestoreString = restoreString.split("[\t]");
                        if (massRestoreString.length == 13) {
                            obj = new RowObj(massRestoreString[0], massRestoreString[1], massRestoreString[2], massRestoreString[3], massRestoreString[4],
                                    massRestoreString[5], massRestoreString[6], massRestoreString[7], massRestoreString[8], salesI, salesV, massRestoreString[11], massRestoreString[12]);
                        } else {
                            continue;
                        }

                    } else {
                        continue;
                    }

                }

                //проверяем дубликаты, если они есть агрегируем
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
            String s1 = reader2.readLine();
            if (countRowList2 == 1) {

                countRowList2++;
            } else {
                s2Mass = s1.split("[\t]");

                    // в файле в качестве разделителя дробной части присутствект , а не .
                    double salesV = 0;
                    double salesI = 0;

                    try {
                        salesV = Double.parseDouble((s2Mass[9].replace(",", ".")));
                        salesI = Double.parseDouble((s2Mass[10].replace(",", ".")));
                    } catch (Exception e) {
                        salesV = 0;
                        salesI = 0;
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
//Сборка строк с ошибками и передача из в Модальное окно
    public void strErrorBuilder(){

    }

// Пишим в файл
public void writeOutPutFile(String currentdir,String[] shapka, List<RowObj> result ) throws IOException {

       // BufferedWriter writer = new BufferedWriter(new FileWriter(currentdir));
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
//Изменение


    }


    }


