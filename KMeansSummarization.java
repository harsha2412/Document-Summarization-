/**
 * @(#)KMeansSummarization.java
 *
 *
 * @author 
 * @version 1.00 2013/11/17
 */
import java.io.*;//FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.*;
import java.util.Arrays;
import java.io.*;
import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class KmeansSummarization {
   
    public static int findSentence(double[] l1,double[] l2,int size,int rank)
    {
     int index=0;
     for(int i=0;i<size;i++)
     {
         if(l1[i]==l2[rank])
             index=i;
     }
     return index;
     //sentence index is cl.get(index)
    }
    
 //static double C[][]=new doublep[][];
    public static double[] newCentroid(ArrayList<Integer> l,double[][] C,int nos)
    {
        double cen[]=new double[nos];
       // int sum;
        for(int i=0;i<nos;i++)
        {
        for(int j=0;j<l.size();j++)
        {
        //System.out.print(C[l.get(j)][i]+ " ");
         cen[i]=cen[i]+C[l.get(j)][i];   
         
        }
        //System.out.println();
        }
         for(int i=0;i<nos;i++)
        {
            cen[i]=cen[i]/l.size();
        }
        return cen;
    }
    public static int findMin(double[] dis,int k)
    {
    	double currentValue = dis[0]; 
		int smallestIndex = 0;
		for (int j=1; j < k; j++) {
			if (dis[j] < currentValue)
                        { 
				currentValue = dis[j];
                                smallestIndex = j;
                        }
		}
                return smallestIndex;
    }
    //public static 
public static double calculateDistance(double[] l1,double[] l2 ,int n)
{
	double d=0;
	for(int i=0;i<n;i++)
	{
		double t=l1[i]-l2[i];
		if(t<0)
			t=-t;
		d+=t;
        }
        return d;
}
  
    	public static void main(String args[]) throws FileNotFoundException, IOException
    {
         long start= System.currentTimeMillis();
        Scanner sin;
        int i=0,nos,words,j=0;
        String t=" ";
         String dirName="E:/study/7thsem/DM/summary/";
         
         String s[];
         String Para=" ";
     ArrayList<String>  wordList = new ArrayList<String>();
     ArrayList<String>  sentences = new ArrayList<String>();
         File f=new File(dirName);
        if(f.isDirectory())
        {
           // System.out.println("directory");
            s=f.list();
            for( j=0;j<s.length;j++)
            {
            
                 File f1=new File(dirName+"/"+s[j]);
                 try
                 {
                	  FileReader in = new FileReader(f1);
                          BufferedReader br = new BufferedReader(in);
                            String line = null;  
                             while ((line = br.readLine()) != null){
        
                               System.out.println(line);
       
                                     Para+=line;
                                                                //System.out.println(br.readLine());
                                                                            //   System.out.println("In while");
                                                                   }
                 
            
               }    
                 catch(Exception e)
                 {	System.out.println("file cannot be opened");}
                 
            }
            
        }
         StringTokenizer st = new StringTokenizer(Para,".");
while (st.hasMoreElements()) {
                          t=(String) st.nextElement();
                          
    
                         sentences.add(t);
    
		}

StringTokenizer tokenizer = new StringTokenizer(Para, " \t\r\n.");
while(tokenizer.hasMoreElements()) {
    t=(String)tokenizer.nextElement();
    if(wordList.contains(t));
    else
        wordList.add(t);
    
}
words=wordList.size();
//System.out.println("Total Number Of Unique Words :"+ wordList.get(words-1));
nos=sentences.size();
System.out.println();
System.out.println();
System.out.println("Total Number Of Unique Words :"+words+" Total Sentences = "+nos);

//System.out.println(sentences.get(nos-1));
double A[][]=new double[words][nos];
int L[][]=new int[words][nos];//Frequency
double G[]=new double[words];
int scount[]=new int[words];//no of sentences containing each word

for(i=0;i<words;i++)
{
    for(j=0;j<nos;j++)
    {
        if(sentences.get(j).contains(wordList.get(i)))
        L[i][j]=L[i][j]+1;
    }
    
}
//L is the frequency matrix
int cnt=0;
   for(i=0;i<words;i++)
{
 for(j=0;j<nos;j++)
 {
     if(L[i][j]!=0)
     {
         cnt++;
         //System.out.println("Here");
         scount[i]=cnt;
     }
 }
 cnt=0;
}
   //Inverse Sentence Frequency
   for(i=0;i<words;i++)
{
    G[i]=(Math.log10((nos)/scount[i]))/(Math.log10(2));
}
   for(i=0;i<words;i++)
{
    for(j=0;j<nos;j++)
    {
        
       A[i][j]=L[i][j]*G[i]; 
    }
}
Matrix A1 = new Matrix(A);  
       	SingularValueDecomposition svd = A1.svd();
     Matrix U = svd.getU();
     Matrix Sigma = svd.getS();
      Matrix V = svd.getV();
      ArrayList<Double>  VA = new ArrayList<Double>();
        double[][] C = V.getArray();
       //System.out.println();
       //System.out.println();
       System.out.println();
       // V.print(V);
        int summaryLength=8;
    //Apply Kmeans on C to get cluster 
    int k =3;
     //ArrayList<String>  wordList = new ArrayList<String>();
    ArrayList<Integer> cl1 = new ArrayList<Integer>();
    ArrayList<Integer> cl2 = new ArrayList<Integer>();
    ArrayList<Integer>  cl3 = new ArrayList<Integer>();
     ArrayList<Integer> t1 = new ArrayList<Integer>();
    ArrayList<Integer> t2 = new ArrayList<Integer>();
    ArrayList<Integer>  t3 = new ArrayList<Integer>();
    double Distance[][] = new double[nos][k];
    //number of clusters
    // arbitrary centroid is 1st sentence,last ans midddle
    int c1=0;
    	int c2=nos-1;
    	int c3=(nos-1)/2;
    	//System.out.println("C3 =" + c3);
    	for(i=0;i<nos;i++)
    	{
    		
    			Distance[i][0]=calculateDistance(C[i],C[c1],nos);
                        Distance[i][1]=calculateDistance(C[i],C[c2],nos);
                         Distance[i][2]=calculateDistance(C[i],C[14],nos);
    		
    	}
       /* for(i=0;i<nos;i++)
    	{
            System.out.print(Distance[i][0]+" "+ Distance[i][1]+" "+ Distance[i][2]);
            System.out.println();
                    
        }*/
        for(i=0;i<nos;i++)
    	{
    	if(findMin(Distance[i],k)==0)
            cl1.add(i);
        if(findMin(Distance[i],k)==1)
             cl2.add(i);
        else 
            cl3.add(i);
            
        }
    
        double[] cen1=newCentroid(cl1, C, nos);
        double[] cen2=newCentroid(cl2, C, nos);
        double[] cen3=newCentroid(cl3, C, nos);
        int flag=-1;
       // int ki=0;
        while(flag!=1)
        {
            
            // System.out.println("In while");
            t1=cl1;
            t2=cl2;
            t3=cl3;
            for(i=0;i<cl1.size();i++)
            {
                cl1.remove(i);
            }
            for(i=0;i<cl2.size();i++)
            {
                cl2.remove(i);
            }
            for(i=0;i<cl3.size();i++)
            {
                cl3.remove(i);
            }
          for(i=0;i<nos;i++)
    	{
    		
    			Distance[i][0]=calculateDistance(C[i],cen1,nos);
                        Distance[i][1]=calculateDistance(C[i],cen2,nos);
                         Distance[i][2]=calculateDistance(C[i],cen3,nos);
    		
    	}
          for(i=0;i<nos;i++)
    	{
    	if(findMin(Distance[i],k)==0)
            cl1.add(i);
        if(findMin(Distance[i],k)==1)
             cl2.add(i);
        else 
            cl3.add(i);
            
        }
          
          if(t1==cl1 && t2==cl2 && t3==cl3)
          {
               //System.out.println("Success!");
              flag=1;
          }
            cen1=newCentroid(cl1, C, nos);
       cen2=newCentroid(cl2, C, nos);
       cen3=newCentroid(cl3, C, nos);
          
            
        }
       // System.out.println("ki ="+ki);
        //Calculate term feature for each sentence in all clusters
        //top 2 sentences per cluster
        double[] ST1=new double[cl1.size()];
       double[] ST2=new double[cl2.size()];
       double[] ST3=new double[cl3.size()];
        double[] TF1=new double[cl1.size()];
        for(i=0;i<cl1.size();i++)
        {
            for(j=0;j<nos;j++)
            {
            TF1[i]+=A[cl1.get(i)][j]*L[cl1.get(i)][j];
            ST1[i]=TF1[i];
                    }
        }
      double[] TF2=new double[cl2.size()];
        for(i=0;i<cl2.size();i++)
        {
            for(j=0;j<nos;j++)
            {
            TF2[i]+=A[cl2.get(i)][j]*L[cl2.get(i)][j];
             ST2[i]=TF2[i];
                    }
        }
        double[] TF3=new double[cl3.size()];
        for(i=0;i<cl3.size();i++)
        {
            for(j=0;j<nos;j++)
            {
            TF3[i]+=A[cl3.get(i)][j]*L[cl3.get(i)][j];
            ST3[i]=TF3[i];
                    }
        }
        /*ST1=Sort(ST1,cl1.size());
         ST2=Sort(ST2,cl1.size());
          ST2=Sort(ST2,cl1.size());*/
        
        Arrays.sort(ST1);
         Arrays.sort(ST2);
          Arrays.sort(ST3);
          int[] cluster1=new int[2];
          int[] cluster2=new int[2];
          int[] cluster3=new int[2];
          int clu11=findSentence(TF1,ST1,cl1.size(),(cl1.size()-1));
          int clu12=findSentence(TF1,ST1,cl1.size(),(cl1.size()-2));
          int clu21=findSentence(TF2,ST2,cl2.size(),(cl2.size()-1));
          int clu22=findSentence(TF2,ST2,cl2.size(),(cl2.size()-2));
          int clu31=findSentence(TF3,ST3,cl3.size(),(cl3.size()-1));
          int clu32=findSentence(TF3,ST3,cl3.size(),(cl3.size()-2));
          //int[] summary=new int[6];
        cluster1[0]=cl1.get(clu11);
           cluster1[1]=cl1.get(clu12);
           cluster2[0]=cl2.get(clu21);
           cluster2[1]=cl2.get(clu22);
           cluster3[0]=cl3.get(clu31);
           cluster3[1]=cl3.get(clu32);
            ArrayList<Integer> summary = new ArrayList<Integer>();
          //  int t=0;
            for(i=0;i<2;i++)
            {
                if(summary.contains(cluster1[i]));
    else
               summary.add(cluster1[i]);
            }
            for(i=0;i<2;i++)
            {
                if(summary.contains(cluster2[i]));
    else
               summary.add(cluster2[i]);
            }
            
            for(i=0;i<2;i++)
            {
                if(summary.contains(cluster3[i]));
    else
               summary.add(cluster3[i]);
            }
            
            Collections.sort(summary);
        System.out.println("Summary");
         for(i=0;i<summary.size();i++)
         {
            
             System.out.println(sentences.get(summary.get(i)));
         }
         
        //Sort STi
       
    
   
   
   
   long end = System.currentTimeMillis();
   long total =end-start;
   System.out.println("Time Taken ="+total);
   
    }
    
 // end main  
}
//end class

