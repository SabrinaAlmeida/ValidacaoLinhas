/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desafio.pkg2.validacao.de.linhas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author sabri
 */
public class Desafio2ValidacaoDeLinhas {
    
    public static ArrayList<String> listaLinhas = new ArrayList<>();
    public static String caminho;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        System.out.println("Informe o caminho do arquivo: ");
        Scanner scanner = new Scanner(System.in);
        caminho = scanner.next();
        LerTXT();
        AnalisaPalavras();       
        EscreveTXT();
        System.out.println("Arquivo lido com sucesso!");       
    }
    

    public static void LerTXT() throws FileNotFoundException {
        try {
            FileReader file = new FileReader(caminho);
            try (BufferedReader lerArq = new BufferedReader(file)) {
                String linha = lerArq.readLine();
                
                while (linha != null) {                   
                    listaLinhas.add(linha);
                    linha = lerArq.readLine();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void AnalisaPalavras(){
         for(int i = 0; i< listaLinhas.size(); i ++){
            int chaveAbre = 0, chaveFecha = 0, colcheteAbre = 0,colcheteFecha = 0, parenteseAbre = 0, parenteseFecha = 0;
            String palavra = listaLinhas.get(i);
            for(int n = 0; n< palavra.length(); n++){
                char caractere = palavra.charAt(n);
                switch (caractere){
                        case '{':
                            chaveAbre++;
                            break;
                        case '}':
                            chaveFecha++;
                            break;
                        case '[':
                            colcheteAbre++;
                            break;
                        case ']':
                            colcheteFecha++;
                            break;
                        case '(':
                            parenteseAbre++;
                            break;
                        case ')':
                            parenteseFecha++;
                        default:
                            break;
                }                       
            }
            if(chaveAbre == chaveFecha && colcheteAbre == colcheteFecha && parenteseAbre == parenteseFecha){

                String conteudo = palavra.replaceAll("^\\{}\\[]\\(\\)", "");
                if (conteudo.isEmpty())                   
                    continue;                 
                char[] CaracteresDaPalavra = conteudo.toCharArray();
                if(CoerenciaDosCaracteres(CaracteresDaPalavra))
                    listaLinhas.set(i, palavra.concat(" - OK"));
                else
                    listaLinhas.set(i, palavra.concat(" - Inválido"));
                               
            }
            else
                listaLinhas.set(i, palavra.concat(" - Inválido"));
        }
    }
    

    private static void EscreveTXT() throws IOException {
        try (Writer arquivo = new BufferedWriter(new FileWriter(caminho, false))) {
            for(int x = 0; x < listaLinhas.size(); x++){
                arquivo.write(listaLinhas.get(x).concat("\n"));
            }
            arquivo.close();
        }
    }
    
    
    
    
    static boolean CoerenciaDosCaracteres(char[] caracteres) {
        Stack pilha = new Stack();
        try{
        for(int i=0;i<caracteres.length;i++)
        {
            if (caracteres[i] == '{' || caracteres[i] == '(' || caracteres[i] == '[')
                pilha.push(caracteres[i]);

            if (caracteres[i] == '}' || caracteres[i] == ')' || caracteres[i] == ']')
            {               
                char proximoDaPilha;
                if (pilha.isEmpty())
                    return false;
                else{
                    proximoDaPilha = (Character)pilha.lastElement();
                }
                if((proximoDaPilha == '(' && caracteres[i] == ')')){
                    pilha.pop();
                    continue;
                }
                else if((proximoDaPilha == '{' && caracteres[i] == '}')){
                    pilha.pop();
                    continue;
                }
                else if((proximoDaPilha == '[' && caracteres[i] == ']')){
                    pilha.pop();
                    continue;
                }
                else 
                    return false;
            }
        }
        return pilha.isEmpty();
        
        }
        catch(Exception e){
            return false;
        }    
    }
}