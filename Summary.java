/**
 * @(#)Summary.java
 *
 *
 * @author 
 * @version 1.00 2013/11/15
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//single document summarization
// string sentence matrix :contains the sentences in the doc
//
/**
 *
 * @author Lily
 */
import java.io.*;//FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.*;
import java.io.*;
import Jama.Matrix;
import Jama.SingularValueDecomposition;
public class Summary {
    
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
         long start= System.currentTimeMillis();
        Scanner sin;
        int i=0,nos,words,j=0;
        String t=" ";
         String dirName="E:/study/7thsem/DM/summary/";
         //String dirName="E:/study/7thsem/DM/summary1/";
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
System.out.println(wordList.get(words-1));
nos=sentences.size();
System.out.println(words+"  "+nos);

//System.out.println(sentences.get(nos-1));
double A[][]=new double[words][nos];
int L[][]=new int[words][nos];
double G[]=new double[words];
int scount[]=new int[words];

for(i=0;i<words;i++)
{
    for(j=0;j<nos;j++)
    {
        if(sentences.get(j).contains(wordList.get(i)))
        L[i][j]=L[i][j]+1;
    }
    
}

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
/* for(i=0;i<words;i++)
{
    for(j=0;j<nos;j++)
    {
        
    System.out.print(A[i][j]+ " ");
    }
    System.out.println();
} */
       Matrix A1 = new Matrix(A);  
       	SingularValueDecomposition svd = A1.svd();
     Matrix U = svd.getU();
     Matrix Sigma = svd.getS();
      Matrix V = svd.getV();
      ArrayList<Double>  VA = new ArrayList<Double>();
       double[][] C = V.getArray();
       System.out.println();
       System.out.println();
       System.out.println();
        V.print(V);
        System.out.println();
        int summaryLength=5;
        int summary[]=new int[summaryLength];
        //choose the col(representing a sentence) for which V val is maximum
       // System.out.println(C[0][0]);
        // System.out.println(C.length);
        // System.out.println(C[0].length);
        for(i=0;i<nos;i++)
        {
        	for(j=0;j<nos;j++)
        	{
        		//System.out.print(C[i][j]);
        		if(C[i][j]>0)
        		{
        		//	System.out.println("Positive");
        			VA.add(C[i][j]);
        		}
        			
        			
        	}
        }
        //System.out.println(VA.size());
       /* for(i=0;i<VA.size();i++)
        {
        	System.out.print(VA.get(i));
        }*/
        System.out.println();
        System.out.println();
        System.out.println();
        Collections.sort(VA);
       /* for(i=0;i<summaryLength;i++)
        {
        	System.out.print(VA.get(i));
        }*/
        System.out.println();
        System.out.println();
        System.out.println();
        i=0;
        for(i=0;i<summaryLength;i++) 
        {
        
        for(int k=0;k<nos;k++)
        {
        	for(j=0;j<nos;j++)
        	{
        		if(C[k][j]==VA.get(i))
        		{
        			//System.out.println(k);
        			summary[i]=k;
        		
        			
        			
        		}
        			
        	}
        }
        }
        Arrays.sort(summary);
       System.out.println("Summary");
      for(i=0;i<summaryLength;i++)
      {
      //	System.out.println(summary[i]);
      	System.out.println(sentences.get(summary[i]));
      }
        long end = System.currentTimeMillis();
   long total =end-start;
   System.out.println("Time Taken ="+total);
//End main        
}
//end class    
}
