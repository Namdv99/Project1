package tsp1;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class TSP1 {

    public static int city = 51;
    public int khoangcach[][] = new int[city][city];
    public int toado[] = new int[3 * city];
    public int max=100000;
    public static int n =50;
    public Random ran = new Random();
    ArrayList<ArrayList<Integer>> nghiem = new ArrayList<>();
    ArrayList<Integer> thichnghi = new ArrayList<>();

    @SuppressWarnings("empty-statement")
    public void city() {
        File file = new File("inputTSP.txt");
        try (Scanner f = new Scanner(file)) {
            int str = 0;
            while (f.hasNext()) {
                toado[str] = f.nextInt();
                str++;
            };
        } catch (Exception e) {
        };
        for (int i = 0; i < city; i++) {
            for (int j = 0; j < city; j++) {
                khoangcach[i][j] = (int) Math.sqrt((toado[3 * i + 1] - toado[3 * j + 1]) * (toado[3 * i + 1]
                        - toado[3 * j + 1]) + (toado[3 * i + 2] - toado[3 * j + 2]) * (toado[3 * i + 2] - toado[3 * j + 2]));
            }
        };
    }

    @SuppressWarnings("empty-statement")
    public void khoitao() {
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> a = new ArrayList<>();
            ArrayList<Integer> b = new ArrayList<>();
            for (int t = 0; t < city; t++) {
                b.add(t);
            }
            for (int j = 0; j < city; j++) {
                int h = ran.nextInt(b.size());
                a.add(b.get(h));
                b.remove(h);
            }
            nghiem.add(a);
        };
    }

    @SuppressWarnings("empty-statement")
    public void danhgia() {
        for (int i = 0; i < n; i++) {
            int k = 0;
            for (int j = 0; j < city - 1; j++) {
                k += khoangcach[nghiem.get(i).get(j)][nghiem.get(i).get(j + 1)];
            };
            thichnghi.add(k);
        };
    }

    @SuppressWarnings("empty-statement")
    public void chonloc() {
        ArrayList<Integer> temp=new ArrayList<>();
       
      
        for (int i = 0; i < thichnghi.size(); i++) {
            temp.add(max/thichnghi.get(i));
        };
        for(int i=1;i<temp.size();i++)temp.set(i, temp.get(i-1)+temp.get(i));
        for(int i=0;i<n*6/10;i++){
            int h=ran.nextInt(temp.get(temp.size()-1));
            int j=1; 
            while (h>temp.get(j))j++;
            nghiem.add(nghiem.get(j));  
            thichnghi.add(thichnghi.get(j));
            nghiem.remove(j);
            thichnghi.remove(j);
            for(int t=j+1;t<temp.size();t++){
                temp.set(t, temp.get(t)-temp.get(j)+temp.get(j-1));                
            };
            temp.remove(j);
        }       
    }
    
  
    public void laighep() {
        for (int k = 0; k < n * 2 / 10; k++) {
           
            int cha = ran.nextInt(nghiem.size());
            int me = ran.nextInt(nghiem.size());
            int vt = ran.nextInt(city - 1);
            
            // Tao con lai 1
            ArrayList<Integer> con1 = new ArrayList<>();
            
            for (int i = 0; i < vt; i++)con1.add(nghiem.get(cha).get(i));
            for (int i = 0; i < nghiem.get(me).size(); i++) {
                int h = 0;
                for (int j = 0; j < vt; j++) {
                    if (Objects.equals(nghiem.get(me).get(i), nghiem.get(cha).get(j))) {
                        h++;
                        break;
                    }
                };
                if (h == 0) {
                    con1.add(nghiem.get(me).get(i));
                }
            };
            nghiem.add(con1);
            // add do thich nghi con 1
            int t = 0;
            for (int j = 0; j < city - 1; j++) {
                t += khoangcach[con1.get(j)][con1.get(j + 1)];
            };
            thichnghi.add(t);
            // Tao con lai 2
            ArrayList<Integer> con2 = new ArrayList<>();
            for (int i = 0; i < nghiem.get(cha).size(); i++) {
                int h = 0;
                for (int j = vt; j < city; j++) {
                    if (Objects.equals(nghiem.get(cha).get(i), nghiem.get(me).get(j))) {
                        h++;
                        break;
                    }
                };
                if (h == 0) {
                    con2.add(nghiem.get(cha).get(i));
                }
            };
            for (int i = vt; i < city; i++) {
                con2.add(nghiem.get(me).get(i));
            }
            nghiem.add(con2);
            // add do thich nghi con 2
            t = 0;
            for (int j = 0; j < city - 1; j++) {
                t += khoangcach[con2.get(j)][con2.get(j + 1)];
            };
            thichnghi.add(t);
        };      
    }

    @SuppressWarnings("empty-statement")
    public void dotbien() {
        for (int i = 0; i <= 2*n/100; i++) {
            int vitri1 = ran.nextInt(city);
            int vitri2 = ran.nextInt(city);

            int gen = ran.nextInt(n * 6 / 10);
            ArrayList<Integer> dotbien = new ArrayList<>();
            for (int j = 0; j < city; j++) {
                if (j == vitri1) {
                    dotbien.add(nghiem.get(gen).get(vitri2));
                } else {
                    if (j == vitri2) {
                        dotbien.add(nghiem.get(gen).get(vitri1));
                    } else {
                        dotbien.add(nghiem.get(gen).get(j));
                    }
                }
            }
            nghiem.add(dotbien);
            //tinh do thich nghi
            int t = 0;
            for (int j = 0; j < city - 1; j++) {
                t += khoangcach[dotbien.get(j)][dotbien.get(j + 1)];
            };
            thichnghi.add(t);
        };
    }

    @SuppressWarnings("empty-statement")
    
    public void taitao() {
        for(int i=0;i<thichnghi.size()-1;i++){
            for(int j=i;j<thichnghi.size();j++){
                if(thichnghi.get(i)>thichnghi.get(j)){
                    //doi cho thichnghi
                    int k=thichnghi.get(i);
                    thichnghi.set(i,thichnghi.get(j));
                    thichnghi.set(j, k);
                    //doi cho nghiem
                    ArrayList<Integer> node=new ArrayList<>();
                    for(k=0;k<city;k++)node.add(nghiem.get(i).get(k));
                    nghiem.set(i,nghiem.get(j));
                    nghiem.set(j,node);
                }
            }
        };
        while(nghiem.size()>n){
            int k=n*8/10+ran.nextInt(nghiem.size()-n*8/10);
            nghiem.remove(k);
            thichnghi.remove(k);
        }       
    } 
    
    public void chonloc2(){
        int i=0;
        while(i<n*4/10){
            nghiem.remove(nghiem.size()-1);
            thichnghi.remove(thichnghi.size()-1);
            i++;
        }
    }
    public void print(){
        System.out.println(thichnghi.get(0));
    }
    
    
    public static void main(String[] args) {
        TSP1 jang = new TSP1();

        jang.city();
        jang.khoitao();
        jang.danhgia();
        for(int i=0;i<500;i++){
            jang.chonloc();
            jang.laighep();
            jang.dotbien();
            jang.taitao();
        };
        jang.print();
    }
}
