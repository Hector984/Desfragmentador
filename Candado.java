package Desfragmentador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Abraxas-OMIO
 */
public final class Candado extends JFrame implements ActionListener {
    private JButton[][] Casilla;   
    private final Object[][][] Tablero;
    private final String[] Constraseña = {"0", "3", "2", "9", "4"}; 
    private JButton jButton1;
    private JButton jButton2; 
    private JButton jButton3;    
    private JPanel Panel_Matriz;   
    private final Color[] colores; 
    private Timer timer;
    
    
    
    class Cola_de_Digitos implements Serializable{
        private final Color color;
        //Numero de fragmentos por archivo        
        public int n_digitos = 10;//Representa las filas

        public Cola_de_Digitos(Color color, int digito, int j, int filas) {
            this.color = color;
            //Son las filas
            for (int i = 0; i < filas; i++) {
                //Asignamos y despues incrementamos la variable
                this.casillaAleatoria((new Random().nextInt(2)), i, j);
            }   
        }
        
        public void casillaAleatoria(int aleatorio, int i, int j) {
        	
        	if(aleatorio == 0) {
        		Tablero[0][i][j] = (new Random().nextInt(9)) + 1;//digito++;
                Casilla[i][j].setText(String.valueOf(Tablero[0][i][j]));
                Tablero[1][i][j] = color;
                Casilla[i][j].setBackground(color);
                //Establecer un color para el texto del Casilla
                Casilla[i][j].setForeground(Color.BLACK); 
        	}else {
        		Tablero[0][i][j] = 0;//digito++;
                Casilla[i][j].setText("");
                Tablero[1][i][j] = Color.WHITE;
                Casilla[i][j].setBackground(Color.WHITE);
                //Establecer un color para el texto del Casilla
                Casilla[i][j].setForeground(Color.BLACK); 
        	}
        	
        }
           
    }
    
    protected ArrayList<Cola_de_Digitos> Candado;
    protected int n, m;
    
    /**************************************************************************
            Constructor de la clase N_Puzzle_CG_Heuristicas.
     * @param n: Numero de renglones del tablero
     * @param m: Número de columnas del tablero
    **************************************************************************/    
    public Candado(int n, int m){
        this.n = n;
        this.m  = m;
        //Declaramos y llenamos el tablero auxiliar con una configuración aleatoria
        //El tablero va a ser una matriz tridimensional, con 2 capas
        //La primera capa van a ser los frgamentos de cada archivo
        //La segunda capa va ser el color
        Tablero = new Object[2][n][m];
        //Inicializamos los colores de los archivos
        colores = new Color[5];
        colores[0] = Color.YELLOW;
        colores[1] = Color.RED;
        colores[2] = Color.ORANGE;
        colores[3] = Color.LIGHT_GRAY;
        colores[4] = Color.BLUE;
        //Iniciamos los componentes graficos de la aplicación
        iniciaComponentes();
    }   
    
    /**************************************************************************
           Método para inciiar los componentes graficos de la aplicación.
    **************************************************************************/     
    public void iniciaComponentes(){
        
        //Ponemos estilo libre mediante la instrucción setLayout(null);
        setLayout(null);
        //Ponemos una fuente para el titulo del panel
        Font fuente = new Font("Courier New", Font.BOLD, 12);
        //Inicilizamos la matriz de botones que será nuestro tablero
        Casilla = new JButton[n][m];
        //Declaramos el panel que contendra el tablero
        Panel_Matriz = new JPanel();
        //Agregamos la fuente de letra al panel
        Panel_Matriz.setFont(fuente);
        //Haceos el panel sea visible
        Panel_Matriz.setVisible(true);
        //Dependiendo de las dimensiones del tablero el panel crece o decrece de forma dinamica
        Panel_Matriz.setBorder(BorderFactory.createTitledBorder("Desfragmentador de un Candado"));
        Panel_Matriz.setBounds(20, 20, 520, 300);
         
        //Dividimos el panel en una matriz de n x m para pintar el tablero
        Panel_Matriz.setLayout(new GridLayout(n,m));
        
        //Inicializamos el tablero
        iniciaDigitos(n, m);
        //Finalmente agregamos el panel al JFrame que creamos al incio de la aplicación
        getContentPane().add(Panel_Matriz);
        
        //Nuevamente declaramos un nuevo tipo de letra para los demas elementos del GUI
        fuente = new Font("Courier New", Font.BOLD, 16);
        //Declaramos los botones        
        jButton1 = new JButton("Descifrado");
        jButton1.setFont(fuente);
        jButton1.addActionListener(this);

        jButton2 = new JButton("Nuevo Candado");
        jButton2.setFont(fuente);      
        jButton2.addActionListener(this);  
        
        jButton3 = new JButton("Salir");
        jButton3.setFont(fuente);      
        jButton3.addActionListener(this);        

        //Dependiendo de las dimensiones del tablero el JFrame se ajusta de manera automatica
        setBounds(0, 0, 580, 460);
        jButton1.setBounds(25, 340, 160, 30);
        this.add(jButton1);
        jButton2.setBounds(215, 340, 180, 30);
        this.add(jButton2);
        jButton3.setBounds(425, 340, 110, 30);
        this.add(jButton3);   
            
        //Agregamos un método de salida por default
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Ponemos en modo visible nuestra ventana
        setVisible(true);
        //Hacemos que la ventana se localice en el centro de la pantalla
        setLocationRelativeTo(null);
        
    }
    
    /**************************************************************************
    
	* @param n: Numero de renglones del tablero
	* @param m: Número de columnas del tablero
	**************************************************************************/ 

    public void iniciaDigitos(int n, int m){
        //Seleccionamos una nueva fuente para los numero del tablero
        Font fuente = new Font("Courier New", Font.BOLD, 14);
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++){
                Tablero[0][i][j] = 0;
                Tablero[1][i][j] = Color.WHITE;
            }
        }
             
        //Empieza la declaración del tablero mediante un for doble
        for (int i = 0; i < n; i++) {
            for (int  j = 0; j < m; j++) {
                Casilla[i][j] = new JButton("");   
                //Añadimos el tipo de letra a las casillas del tablero
                Casilla[i][j].setFont(fuente);                
                //Establecer un color para el fondo del Casilla
                Casilla[i][j].setBackground(Color.WHITE);
                //Establecer un color para el texto del Casilla
                Casilla[i][j].setForeground(Color.BLACK);
                //Agregamos un concierge de eventos
                Casilla[i][j].addActionListener(this);
                //Agregamos cada casilla creada al panel
                Panel_Matriz.add(Casilla[i][j]);
            }
        }         
        
        //Inicializamos el arreglo para los archivos que seran descifrados
        Candado = new ArrayList<>(5);
        //el indice se basa en el numero de colores
        for(int i = 0; i < 5; i++) {
            Cola_de_Digitos obj = new Cola_de_Digitos(colores[i], 0, i, n);
            Candado.add(obj);
        }
                
    }//FIn inicia digitos         

    /**************************************************************************
        Método heredado de la interfaz ActionListener, el cual maneja 
        todos los eventos presentes en la aplicación en cualquier 
        momento.
     * @param e: Corresponde a la ruta donde se encuentre actualmente
    **************************************************************************/    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jButton1){
            comienzaSimulacion();
        }else if(e.getSource() == jButton2){
            nuevaSimulacion();
        }else if(e.getSource() == jButton3){
            System.exit(0);
        }
    }    
    
    
    private void comienzaSimulacion(){
        int i, j;
        Color[] aux_C = new Color[5];
        Color sol = Color.WHITE;
        int[] pos = new int[5];
        boolean salida  = true;
        Random r = new Random();
        //Mostrar solucion
        LinkedList<Object[][][]> resultado = new LinkedList<>();
        do{    
            //Primer for para el tipo de combinacion
            for(j = 0; j < 5; j++){
                i = r.nextInt(10);
                pos[j] = i;
                aux_C[j] = (Color) Tablero[1][i][j];
                Tablero[1][i][j] = sol;     
                Object[][][] TB = new Object[2][n][m];
                //TB = Tablero //MAL copia por referencia
                for (int m1 = 0; m1 < 2; m1++) {
                    for (int n1 = 0; n1 < n; n1++) {
                        System.arraycopy(Tablero[m1][n1], 0, TB[m1][n1], 0, m);
                    }
                }
                
                resultado.add(TB);                              
            }

            
           //Reestablezco la configuración inicial
            for(j = 0; j < 5; j++){
                Tablero[1][pos[j]][j] = aux_C[j];             
            }    
            
            //Busqueda de contraseña
            for(j = 0; j < 5; j++){
                if(!(String.valueOf(Tablero[0][pos[j]][j]).equals(Constraseña[j]))){
                    break;
                }
            }
            if(j == 5){
                salida = false;
            }else{
                Object[][][] TB = new Object[2][n][m];
                for (int m1 = 0; m1 < 2; m1++) {
                    for (int n1 = 0; n1 < n; n1++) {
                        System.arraycopy(Tablero[m1][n1], 0, TB[m1][n1], 0, m);
                    }
                }
                resultado.add(TB); 
            }
        }while(salida);

        int tamaño = resultado.size();
        timer = new Timer(3, new ActionListener() {
            private int i = 0;

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (i >= tamaño) {
                    timer.stop();
                    return;
                }
                desplegarTablero(resultado.get(i));
                i++;
            }
        });
        timer.start();
          
    }//Llave que cierra comienzaSimulacion()    

    public void desplegarTablero(Object[][][] T_P){  
        //System.out.println("Entro una vez");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                //Syste0m.out.print(T_P[0][i][j] + " ");
                Casilla[i][j].setText(String.valueOf(T_P[0][i][j]));
                Casilla[i][j].setBackground((Color) T_P[1][i][j]);
                Casilla[i][j].setForeground(Color.BLACK);    
            }
            //System.out.println("");
        }
    }//Fin desplegar tablero
    

    private void nuevaSimulacion() { 
    	
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Tablero[0][i][j] = 0;
            }
        }

        //Empieza la declaración del tablero mediante un for doble
        for (int i = 0; i < n; i++) {
            for (int  j = 0; j < m; j++) {   
                //Reiniciamos las etiquetas
                Casilla[i][j].setText("");                
                //Establecer un color para el fondo del Casilla
                Casilla[i][j].setBackground(Color.WHITE);
                //Establecer un color para el texto del Casilla
                Casilla[i][j].setForeground(Color.BLACK);
            }
        }          
        
        //Inicializamos el arreglo para los archivos que seran desfragmentados
        Candado = new ArrayList<>(5);
        for(int i = 0; i < 5; i++) {
            Cola_de_Digitos obj = new Cola_de_Digitos(colores[i], 0, i, n);
            Candado.add(obj);
        }      
    }//Fin nueva simulacion
    
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
        	//10 filas por 5 columnas
            new Candado(15, 5).setVisible(true);
        });
    }    
}
