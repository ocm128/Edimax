package edit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/*
 * Editor_Principal.java
 *
 * Created on mayo de 2006, 19:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * @author Kalasni
 */


public class Editor_Principal extends JFrame
  implements ActionListener
{
  private JToolBar jtbar;
  private JMenuBar jmbar;
  private JScrollPane scroller;
  private JDesktopPane jdesk;
  private JTabbedPane jtabbedPane;
  private JInternalFrame[] framesInternos;
  private JTextArea[] taArray;
  private String[] nombreFicheroSalva;
  private boolean[] hayCambio;
  private JTextArea tarea;
  private JTextArea taImprimir;
  private int indice;
  private int altura;
  private int anchura;
  private int docNum = 1;
  private int indiceTab = 0;
  private StringBuilder strbuild;
  private String cadBuscada;
  private Font fnt;
  private Font fntPrefe;
  private Color colorFondo;
  private Color colorTexto;
  private JPanel norPanel;
  private JPanel poscursorPanel;
  private JPanel centroPanel;
  private OyenteEtiquetaCursor oyenEtiqueCur;
  private UndoAction undoAction;
  private RedoAction redoAction;
  private CambiaFuentes cambiaFuentes;
  private UndoManager undo = new UndoManager();

  private JPopupMenu jpopmenu = new JPopupMenu();
  MouseListener jpopuplistener;
  private JMenu FICHERO;
  private JMenu EDITAR;
  private JMenu FUENTES;
  private JMenu COLOR_FONDO;
  private JMenu COLOR_TEXTO;
  private JMenu PREFERENCIAS;
  private JMenu ACERCA_DE;
  private JMenuItem nuevoItem;
  private JMenuItem abrirItem;
  private JMenuItem salvarItem;
  private JMenuItem salvarComoItem;
  private JMenuItem imprimirItem;
  private JMenuItem salirItem;
  private JMenuItem cortarItem;
  private JMenuItem copiarItem;
  private JMenuItem pegarItem;
  private JMenuItem encontrarItem;
  private JMenuItem encontrarNextItem;
  private JMenuItem selecItem;
  private JMenuItem undoItem;
  private JMenuItem redoItem;
  private JMenuItem fuentesItem;
  private JMenuItem[] arrayColoresFon = new JMenuItem[13];

  private JMenuItem[] arrayColoresText = new JMenuItem[13];
  private JMenuItem pordefectoItem;
  private JMenuItem size14item;
  private JMenuItem size16item;
  private JMenuItem size18item;
  private JMenuItem size20item;
  private JMenuItem acercaItem;
  private JMenuItem copiarPopitem;
  private JMenuItem pegarPopitem;
  private JMenuItem cortarPopitem;
  private JMenuItem selectPopitem;
  private JMenuItem undoPopitem;
  private JMenuItem redoPopitem;
  private JButton nuevoBoton;
  private JButton abrirBoton;
  private JButton imprimirBoton;
  private JButton salvarBoton;
  private JButton cortarBoton;
  private JButton copiarBoton;
  private JButton pegarBoton;
  private JButton limpiarBoton;
  private JButton buscarBoton;
  private JButton buscarNextBoton;
  protected JButton undoBoton;
  protected JButton redoBoton;
  private JCheckBoxMenuItem formatoItem;

  public Editor_Principal()
  {
    super("EdiMax v1.0");

    this.oyenEtiqueCur = new OyenteEtiquetaCursor("Estado del cursor");
    this.undoAction = new UndoAction();
    this.redoAction = new RedoAction();
    this.jpopuplistener = new JPopupListener();
    this.cambiaFuentes = new CambiaFuentes();

    this.jtbar = new JToolBar();
    this.jmbar = new JMenuBar();
    this.scroller = new JScrollPane();
    this.jtabbedPane = new JTabbedPane(3);
    this.framesInternos = new JInternalFrame[20];
    this.taArray = new JTextArea[20];
    this.nombreFicheroSalva = new String[20];
    this.hayCambio = new boolean[20];
    this.taImprimir = new JTextArea();
    this.fnt = new Font("Courier", 0, 13);
    this.fntPrefe = new Font("Dialog", 1, 12);
    this.colorFondo = Color.WHITE;
    this.colorTexto = Color.BLACK;

    this.norPanel = new JPanel();
    this.poscursorPanel = new JPanel(new BorderLayout());
    this.centroPanel = new JPanel(new BorderLayout());

    Container con = getContentPane();

    addMenu();

    addPopMenu();

    this.norPanel.setLayout(new BorderLayout());
    addBotones();

    this.norPanel.add(this.jtbar, "North");

    con.add("North", this.norPanel);

    Dimension minimoTam = new Dimension(0, 500);
    this.jtabbedPane.setMinimumSize(minimoTam);
    this.centroPanel.add(this.jtabbedPane, "Center");
    con.add("Center", this.centroPanel);
    this.poscursorPanel.add(this.oyenEtiqueCur, "South");
    con.add("South", this.poscursorPanel);

    this.anchura = Toolkit.getDefaultToolkit().getScreenSize().width;
    this.altura = Toolkit.getDefaultToolkit().getScreenSize().height;
    setBounds(0, 0, this.anchura, this.altura - 50);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        Editor_Principal.this.cerrarMarcos();
        System.exit(0);
      }
    });
  }

  public void actionPerformed(ActionEvent ae)
  {
    String arg = ae.getActionCommand();

    if ((ae.getSource() == this.nuevoItem) || (ae.getSource() == this.nuevoBoton)) {
      nuevoFichero(null, false);
    } else if ((ae.getSource() == this.abrirItem) || (ae.getSource() == this.abrirBoton)) {
      abrir();
    } else if ((ae.getSource() == this.salvarItem) || (ae.getSource() == this.salvarBoton)) {
      salvar();
    } else if ((ae.getSource() == this.cortarItem) || (ae.getSource() == this.cortarBoton)) {
      cortar();
    } else if ((ae.getSource() == this.copiarItem) || (ae.getSource() == this.copiarBoton)) {
      copiar();
    } else if ((ae.getSource() == this.pegarItem) || (ae.getSource() == this.pegarBoton)) {
      pegar();
    } else if ((ae.getSource() == this.imprimirItem) || (ae.getSource() == this.imprimirBoton)) {
      imprimir();
    } else if ((ae.getSource() == this.encontrarItem) || (ae.getSource() == this.buscarBoton)) {
      encontrar();
    } else if ((ae.getSource() == this.encontrarNextItem) || (ae.getSource() == this.buscarNextBoton))
    {
      encontrarNext();
    } else if (ae.getSource() == this.salvarComoItem) {
      salvarComo();
    } else if (ae.getSource() == this.selecItem) {
      seleccionaTodo();
    } else if (ae.getSource() == this.acercaItem) {
      acercaDe();
    } else if (ae.getSource() == this.formatoItem) {
      setLineaWrap();
    } else if (ae.getSource() == this.limpiarBoton) {
      limpiarArea();
    } else if (ae.getSource() == this.size14item) {
      cambiarFuentesPrefe(14);
    } else if (ae.getSource() == this.size16item) {
      cambiarFuentesPrefe(16);
    } else if (ae.getSource() == this.size18item) {
      cambiarFuentesPrefe(18);
    } else if (ae.getSource() == this.size20item) {
      cambiarFuentesPrefe(20);
    } else if (ae.getSource() == this.pordefectoItem) {
      cambiarFuentesPrefe(12);
    } else if (ae.getSource() == this.salirItem) {
      cerrarMarcos();
      System.exit(0);
    }
    else if (ae.getSource() == this.cortarPopitem) {
      cortar();
    } else if (ae.getSource() == this.copiarPopitem) {
      copiar();
    } else if (ae.getSource() == this.pegarPopitem) {
      pegar();
    } else if (ae.getSource() == this.selectPopitem) {
      seleccionaTodo();
    }
    else if (arg.equals("Amarillo")) { cambiarFondo(Color.yellow);
    } else if (arg.equals("Azul")) { cambiarFondo(Color.blue);
    } else if (arg.equals("Blanco")) { cambiarFondo(Color.white);
    } else if (arg.equals("Cyan")) { cambiarFondo(Color.cyan);
    } else if (arg.equals("Gris")) { cambiarFondo(Color.gray);
    } else if (arg.equals("Gris Oscuro")) { cambiarFondo(Color.darkGray);
    } else if (arg.equals("Gris Claro")) { cambiarFondo(Color.lightGray);
    } else if (arg.equals("Magenta")) { cambiarFondo(Color.magenta);
    } else if (arg.equals("Naranja")) { cambiarFondo(Color.orange);
    } else if (arg.equals("Negro")) { cambiarFondo(Color.black);
    } else if (arg.equals("Rojo")) { cambiarFondo(Color.red);
    } else if (arg.equals("Rosa")) { cambiarFondo(Color.pink);
    } else if (arg.equals("Verde")) { cambiarFondo(Color.green); }
    else if (arg.equals(" Amarillo")) { cambiarColorFuentes(Color.yellow);
    } else if (arg.equals(" Azul")) { cambiarColorFuentes(Color.blue);
    } else if (arg.equals(" Blanco")) { cambiarColorFuentes(Color.white);
    } else if (arg.equals(" Cyan")) { cambiarColorFuentes(Color.cyan);
    } else if (arg.equals(" Gris")) { cambiarColorFuentes(Color.gray);
    } else if (arg.equals(" Gris Oscuro")) { cambiarColorFuentes(Color.darkGray);
    } else if (arg.equals(" Gris Claro")) { cambiarColorFuentes(Color.lightGray);
    } else if (arg.equals(" Magenta")) { cambiarColorFuentes(Color.magenta);
    } else if (arg.equals(" Naranja")) { cambiarColorFuentes(Color.orange);
    } else if (arg.equals(" Negro")) { cambiarColorFuentes(Color.black);
    } else if (arg.equals(" Rojo")) { cambiarColorFuentes(Color.red);
    } else if (arg.equals(" Rosa")) { cambiarColorFuentes(Color.pink);
    } else if (arg.equals(" Verde")) { cambiarColorFuentes(Color.green); }

  }

  private void addPopMenu()
  {
    this.jpopmenu.add(this.cortarPopitem = new JMenuItem("Cortar"));
    this.jpopmenu.add(this.copiarPopitem = new JMenuItem("Copiar"));
    this.jpopmenu.add(this.pegarPopitem = new JMenuItem("Pegar"));
    this.jpopmenu.add(this.selectPopitem = new JMenuItem("Seleccionar todo"));
    this.jpopmenu.addSeparator();
    this.jpopmenu.add(this.undoPopitem = new JMenuItem(this.undoAction));
    this.jpopmenu.add(this.redoPopitem = new JMenuItem(this.redoAction));

    this.cortarPopitem.addActionListener(this);
    this.copiarPopitem.addActionListener(this);
    this.pegarPopitem.addActionListener(this);
    this.selectPopitem.addActionListener(this);
  }

  public JButton und()
  {
    return this.undoBoton;
  }

  public JButton red()
  {
    return this.redoBoton;
  }

  public void cambiarFondo(Color color)
  {
    Color clr = color;
    this.colorFondo = color;
    int numtabs = this.jtabbedPane.getTabCount() - 1;
    for (int i = 0; i <= numtabs; i++)
      this.taArray[i].setBackground(clr);
  }

  public void cambiarColorFuentes(Color color)
  {
    Color clr = color;
    this.colorTexto = color;
    int numtabs = this.jtabbedPane.getTabCount() - 1;
    for (int i = 0; i <= numtabs; i++)
      this.taArray[i].setForeground(clr);
  }

  public void cambiarFuentesJta()
  {
    Font seleccion = this.cambiaFuentes.devuelveFont();

    this.fnt = seleccion;
    int numtabs = this.jtabbedPane.getTabCount() - 1;
    for (int i = 0; i <= numtabs; i++) {
      this.taArray[i].setFont(seleccion);
    }
    this.cambiaFuentes.setVisible(false);
  }

  public void setLineaWrap()
  {
    int numtabs = this.jtabbedPane.getTabCount() - 1;
    if (this.formatoItem.isSelected()) {
      for (int i = 0; i <= numtabs; i++) {
        this.taArray[i].setLineWrap(true);

        this.taArray[i].setWrapStyleWord(true);
      }
    }
    else
      for (int i = 0; i <= numtabs; i++) {
        this.taArray[i].setLineWrap(false);
        this.taArray[i].setWrapStyleWord(false);
      }
  }

  public void cambiarFuentesPrefe(int tamano)
  {
    int tam = tamano;
    this.fntPrefe = new Font("Dialog", 1, tam);
    this.FICHERO.setFont(this.fntPrefe);
    this.EDITAR.setFont(this.fntPrefe);
    this.FUENTES.setFont(this.fntPrefe);
    this.COLOR_FONDO.setFont(this.fntPrefe);
    this.COLOR_TEXTO.setFont(this.fntPrefe);
    this.PREFERENCIAS.setFont(this.fntPrefe);
    this.ACERCA_DE.setFont(this.fntPrefe);

    this.nuevoItem.setFont(this.fntPrefe);
    this.abrirItem.setFont(this.fntPrefe);
    this.salvarItem.setFont(this.fntPrefe);
    this.salvarComoItem.setFont(this.fntPrefe);
    this.imprimirItem.setFont(this.fntPrefe);
    this.salirItem.setFont(this.fntPrefe);

    this.cortarItem.setFont(this.fntPrefe);
    this.copiarItem.setFont(this.fntPrefe);
    this.pegarItem.setFont(this.fntPrefe);
    this.undoItem.setFont(this.fntPrefe);
    this.redoItem.setFont(this.fntPrefe);

    this.encontrarItem.setFont(this.fntPrefe);
    this.encontrarNextItem.setFont(this.fntPrefe);
    this.selecItem.setFont(this.fntPrefe);
    this.fuentesItem.setFont(this.fntPrefe);
    this.formatoItem.setFont(this.fntPrefe);

    this.size14item.setFont(this.fntPrefe);
    this.size16item.setFont(this.fntPrefe);
    this.size18item.setFont(this.fntPrefe);
    this.size20item.setFont(this.fntPrefe);
    this.pordefectoItem.setFont(this.fntPrefe);

    this.acercaItem.setFont(this.fntPrefe);

    for (int i = 0; i <= 12; i++) {
      this.arrayColoresFon[i].setFont(this.fntPrefe);
      this.arrayColoresText[i].setFont(this.fntPrefe);
    }

    this.cortarPopitem.setFont(this.fntPrefe);
    this.copiarPopitem.setFont(this.fntPrefe);
    this.pegarPopitem.setFont(this.fntPrefe);
    this.selectPopitem.setFont(this.fntPrefe);
    this.undoPopitem.setFont(this.fntPrefe);
    this.redoPopitem.setFont(this.fntPrefe);
  }

  public void encontrar()
  {
    try
    {
      if (this.jtabbedPane.getTabCount() < 0) {
        return;
      }

      this.tarea = new JTextArea();
      this.tarea.setText(this.taArray[this.jtabbedPane.getSelectedIndex()].getText());
      this.strbuild = new StringBuilder(this.tarea.getText());
      this.cadBuscada = JOptionPane.showInputDialog(null, "  Buscar texto ");

      if (this.cadBuscada.length() > 0) {
        this.indice = this.strbuild.indexOf(this.cadBuscada);
        this.taArray[this.jtabbedPane.getSelectedIndex()].requestFocus();
        this.taArray[this.jtabbedPane.getSelectedIndex()].setCaretPosition(this.indice);
        this.taArray[this.jtabbedPane.getSelectedIndex()].setSelectionStart(this.indice);
        this.taArray[this.jtabbedPane.getSelectedIndex()].setSelectionEnd(this.indice + this.cadBuscada.length());
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Introduzca texto a buscar");
      }
    }
    catch (IllegalArgumentException iex) {
      JOptionPane.showMessageDialog(null, "No hay coincidencias");
    }
    catch (NullPointerException nex)
    {
    }
  }

  public void encontrarNext()
  {
    try
    {
      if (this.jtabbedPane.getTabCount() < 0) {
        return;
      }

      if (this.cadBuscada.length() > 0) {
        this.indice = this.strbuild.indexOf(this.cadBuscada, this.taArray[this.jtabbedPane.getSelectedIndex()].getCaretPosition());

        this.taArray[this.jtabbedPane.getSelectedIndex()].setCaretPosition(this.indice);
        this.taArray[this.jtabbedPane.getSelectedIndex()].setSelectionStart(this.indice);
        this.taArray[this.jtabbedPane.getSelectedIndex()].setSelectionEnd(this.indice + this.cadBuscada.length());
      }
    }
    catch (IllegalArgumentException iex)
    {
      JOptionPane.showMessageDialog(null, "No hay más coincidencias");
    }
    catch (NullPointerException nex)
    {
    }
  }

  public void cerrarMarco()
  {
    int ind = this.jtabbedPane.getSelectedIndex();
    if (this.hayCambio[ind] != 0) {
      int opcion = JOptionPane.showConfirmDialog(null, "¿Salvar cambios en " + this.framesInternos[ind].getTitle() + "?", "Edit0R", 0, 3);

      if (opcion == 0) {
        if (this.nombreFicheroSalva[ind] == null)
        {
          salvarComo();
        }
        else {
          salvar();
        }
      }
    }
    this.jtabbedPane.removeTabAt(ind);
    this.indiceTab -= 1;
    if (this.jtabbedPane.getTabCount() == 0)
    {
      this.imprimirBoton.setEnabled(false);
      this.salvarBoton.setEnabled(false);
      this.cortarBoton.setEnabled(false);
      this.copiarBoton.setEnabled(false);
      this.pegarBoton.setEnabled(false);
      this.limpiarBoton.setEnabled(false);
      this.buscarBoton.setEnabled(false);
      this.buscarNextBoton.setEnabled(false);
      this.redoBoton.setEnabled(false);
      this.undoBoton.setEnabled(false);
    }
  }

  public void cerrarMarcos()
  {
    int numeroTabs = this.jtabbedPane.getTabCount() - 1;
    if (numeroTabs >= 0)
      for (int i = numeroTabs; i >= numeroTabs; i--) {
        if (this.hayCambio[i] != 0) {
          int opcion = JOptionPane.showConfirmDialog(null, "¿Salvar cambios en " + this.framesInternos[i].getTitle() + "?", "Edit0R", 0, 3);

          if (opcion == 0) {
            if (this.nombreFicheroSalva[i] == null)
            {
              salvarComo();
            }
            else {
              salvar();
            }
          }
        }
        this.jtabbedPane.removeTabAt(i);
      }
  }

  public void abrir()
  {
    JFileChooser jfc = new JFileChooser();
    int eleccion = jfc.showOpenDialog(this);
    if (eleccion == 0)
      try {
        String fich = jfc.getSelectedFile().getAbsolutePath();
        BufferedReader in = new BufferedReader(new FileReader(fich));

        StringBuilder strbuil = new StringBuilder();
        String texto;
        while ((texto = in.readLine()) != null) {
          strbuil.append(texto + "\n");
        }
        this.nombreFicheroSalva[this.indiceTab] = fich;

        String nombre = jfc.getName(jfc.getSelectedFile());
        nuevoFichero(nombre, true);
        int indice = this.jtabbedPane.getSelectedIndex();
        this.taArray[indice].setText(strbuil.toString());
        this.taArray[indice].setCaretPosition(0);
        this.taArray[indice].requestFocus();
      }
      catch (IOException ioe) {
        JOptionPane.showMessageDialog(null, "Error al abrir el archivo", "ERROR", 0);
      }
    else;
  }

  public void nuevoFichero(String nombreFich, boolean guardado)
  {
    String titulo;
    if (!guardado) {
      String titulo = "Documento " + this.docNum;
      this.docNum += 1;
      this.nombreFicheroSalva[this.indiceTab] = null;
    }
    else {
      titulo = nombreFich;
    }
    this.jdesk = new JDesktopPane();
    this.jdesk.setBackground(Color.LIGHT_GRAY);

    this.framesInternos[this.indiceTab] = new JInternalFrame(titulo, true, true, true, true);
    this.taArray[this.indiceTab] = new JTextArea();
    this.taArray[this.indiceTab].setFont(this.fnt);
    this.taArray[this.indiceTab].setCaretPosition(0);
    this.taArray[this.indiceTab].setBackground(this.colorFondo);
    this.taArray[this.indiceTab].setForeground(this.colorTexto);
    this.taArray[this.indiceTab].setTabSize(8);

    this.taArray[this.indiceTab].setLineWrap(true);
    this.taArray[this.indiceTab].setWrapStyleWord(true);
    this.taArray[this.indiceTab].setMargin(new Insets(5, 10, 0, 10));
    this.scroller = new JScrollPane(this.taArray[this.indiceTab]);

    this.framesInternos[this.indiceTab].getContentPane().add(this.scroller);
    this.framesInternos[this.indiceTab].setResizable(true);

    this.framesInternos[this.indiceTab].setSize(650, 450);

    this.jdesk.add(this.framesInternos[this.indiceTab]);

    this.jtabbedPane.addTab(titulo, this.jdesk);

    this.jtabbedPane.setSelectedIndex(this.indiceTab);

    this.framesInternos[this.indiceTab].setVisible(true);
    this.hayCambio[this.indiceTab] = false;

    this.taArray[this.indiceTab].getDocument().addUndoableEditListener(new MiUndoableEditListener());

    this.taArray[this.indiceTab].addCaretListener(this.oyenEtiqueCur);

    this.taArray[this.indiceTab].addMouseListener(this.jpopuplistener);

    this.taArray[this.indiceTab].addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent kevt) {
        Editor_Principal.this.jtabbedPane.setTitleAt(Editor_Principal.this.jtabbedPane.getSelectedIndex(), Editor_Principal.this.framesInternos[Editor_Principal.this.jtabbedPane.getSelectedIndex()].getTitle());

        Editor_Principal.this.hayCambio[Editor_Principal.this.jtabbedPane.getSelectedIndex()] = 1;
      }
    });
    this.framesInternos[this.indiceTab].addInternalFrameListener(new InternalFrameAdapter()
    {
      public void internalFrameClosing(InternalFrameEvent evt) {
        Editor_Principal.this.cerrarMarco();
      }
    });
    this.indiceTab += 1;
    this.abrirBoton.setEnabled(true);
    this.imprimirBoton.setEnabled(true);
    this.salvarBoton.setEnabled(true);
    this.cortarBoton.setEnabled(true);
    this.copiarBoton.setEnabled(true);
    this.pegarBoton.setEnabled(true);
    this.limpiarBoton.setEnabled(true);
    this.buscarBoton.setEnabled(true);
    this.buscarNextBoton.setEnabled(true);
  }

  public void limpiarArea()
  {
    this.taArray[this.jtabbedPane.getSelectedIndex()].setText(null);
    this.taArray[this.jtabbedPane.getSelectedIndex()].requestFocus();
  }

  public void seleccionaTodo()
  {
    this.taArray[this.jtabbedPane.getSelectedIndex()].selectAll();
    this.taArray[this.jtabbedPane.getSelectedIndex()].requestFocus();
  }

  public void copiar()
  {
    this.taArray[this.jtabbedPane.getSelectedIndex()].copy();
  }

  public void cortar()
  {
    JTextArea txa = new JTextArea();
    txa = this.taArray[this.jtabbedPane.getSelectedIndex()];
    this.undoBoton.setEnabled(true);
    txa.cut();
    txa.requestFocus();
  }

  public void pegar()
  {
    JTextArea txa = new JTextArea();
    txa = this.taArray[this.jtabbedPane.getSelectedIndex()];
    int inicio = txa.getCaretPosition();
    txa.paste();
    int fin = txa.getCaretPosition();
    txa.setSelectionStart(inicio);
    txa.setSelectionEnd(fin);
    this.undoBoton.setEnabled(true);
    txa.requestFocus();
  }

  public void salvar()
  {
    int indice = this.jtabbedPane.getSelectedIndex();
    try {
      if (this.nombreFicheroSalva[indice] == null) {
        salvarComo();
      }
      else {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.nombreFicheroSalva[indice])));

        out.write(this.taArray[indice].getText());
        out.close();
        this.jtabbedPane.setTitleAt(indice, this.framesInternos[indice].getTitle());

        this.hayCambio[indice] = false;
      }
    } catch (IOException ioe) {
      JOptionPane.showMessageDialog(null, "Error al guardar el archivo", "ERROR", 0);
    }
  }

  public void salvarComo()
  {
    int indice = this.jtabbedPane.getSelectedIndex();
    JFileChooser jfc = new JFileChooser(this.framesInternos[indice].getTitle());
    int eleccion = jfc.showSaveDialog(new JPanel());
    if (eleccion == 0)
      try {
        String ruta = jfc.getSelectedFile().getAbsolutePath();
        PrintWriter out = new PrintWriter(new FileWriter(ruta));
        out.write(this.taArray[indice].getText());
        out.close();

        this.jtabbedPane.setTitleAt(indice, String.valueOf(jfc.getSelectedFile()));
        this.framesInternos[indice].setTitle(String.valueOf(jfc.getSelectedFile()));
        this.nombreFicheroSalva[(this.indiceTab - 1)] = ruta;

        this.hayCambio[indice] = false;
      } catch (IOException ioe) {
        JOptionPane.showMessageDialog(null, "Error al guardar el archivo", "ERROR", 0);
      }
    else;
  }

  public void addMenu()
  {
    this.FICHERO = new JMenu("Archivo");
    this.FICHERO.add(this.nuevoItem = new JMenuItem("Nuevo"));
    this.FICHERO.add(this.abrirItem = new JMenuItem("Abrir"));
    this.FICHERO.addSeparator();
    this.FICHERO.add(this.salvarItem = new JMenuItem("Salvar"));
    this.FICHERO.add(this.salvarComoItem = new JMenuItem("Salvar como"));
    this.FICHERO.add(this.imprimirItem = new JMenuItem("Imprimir"));
    this.FICHERO.addSeparator();
    this.FICHERO.add(this.salirItem = new JMenuItem("Salir"));
    this.nuevoItem.addActionListener(this);
    this.abrirItem.addActionListener(this);
    this.salvarItem.addActionListener(this);
    this.salvarComoItem.addActionListener(this);
    this.salirItem.addActionListener(this);
    this.imprimirItem.addActionListener(this);
    this.jmbar.add(this.FICHERO);

    this.EDITAR = new JMenu("Editar");
    this.EDITAR.add(this.cortarItem = new JMenuItem("Cortar"));
    this.EDITAR.add(this.copiarItem = new JMenuItem("Copiar"));
    this.EDITAR.add(this.pegarItem = new JMenuItem("Pegar"));
    this.EDITAR.addSeparator();
    this.EDITAR.add(this.selecItem = new JMenuItem("Seleccionar todo"));
    this.EDITAR.add(this.encontrarItem = new JMenuItem("Buscar"));
    this.EDITAR.add(this.encontrarNextItem = new JMenuItem("Buscar siguiente"));
    this.EDITAR.addSeparator();
    this.EDITAR.add(this.undoItem = new JMenuItem(this.undoAction));
    this.EDITAR.add(this.redoItem = new JMenuItem(this.redoAction));
    this.cortarItem.addActionListener(this);
    this.copiarItem.addActionListener(this);
    this.pegarItem.addActionListener(this);
    this.selecItem.addActionListener(this);
    this.encontrarItem.addActionListener(this);
    this.encontrarNextItem.addActionListener(this);
    this.jmbar.add(this.EDITAR);

    this.FUENTES = new JMenu("Fuentes");
    this.FUENTES.add(this.fuentesItem = new JMenuItem("Seleccionar fuentes"));
    this.fuentesItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        Editor_Principal.this.cambiaFuentes.setVisible(true);
        Editor_Principal.this.cambiaFuentes.okboton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent aev) {
            Editor_Principal.this.cambiarFuentesJta();
          }
        });
        Editor_Principal.this.cambiaFuentes.cancelboton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent aev) {
            Editor_Principal.this.cambiaFuentes.setVisible(false);
          }
        });
      }
    });
    this.FUENTES.add(this.formatoItem = new JCheckBoxMenuItem("Ajustar linea"));
    this.formatoItem.addActionListener(this);
    this.jmbar.add(this.FUENTES);

    addColores();

    this.PREFERENCIAS = new JMenu("Preferencias");
    this.PREFERENCIAS.add(this.size14item = new JMenuItem("Tamaño 14"));
    this.PREFERENCIAS.add(this.size16item = new JMenuItem("Tamaño 16"));
    this.PREFERENCIAS.add(this.size18item = new JMenuItem("Tamaño 18"));
    this.PREFERENCIAS.add(this.size20item = new JMenuItem("Tamaño 20"));
    this.PREFERENCIAS.add(this.pordefectoItem = new JMenuItem("Tamaño original"));
    this.size14item.addActionListener(this);
    this.size16item.addActionListener(this);
    this.size18item.addActionListener(this);
    this.size20item.addActionListener(this);
    this.pordefectoItem.addActionListener(this);
    this.jmbar.add(this.PREFERENCIAS);

    this.ACERCA_DE = new JMenu("Acerca de");
    this.ACERCA_DE.add(this.acercaItem = new JMenuItem("Acerca de"));
    this.acercaItem.addActionListener(this);
    this.jmbar.add(this.ACERCA_DE);

    setJMenuBar(this.jmbar);
  }

  public void addBotones()
  {
    this.nuevoBoton = new JButton(new ImageIcon(getClass().getResource("iconos/nuevo.png")));

    this.nuevoBoton.setToolTipText("Nuevo archivo");
    this.nuevoBoton.setEnabled(true);
    this.jtbar.add(this.nuevoBoton);

    this.abrirBoton = new JButton(new ImageIcon(getClass().getResource("iconos/abrir.png")));

    this.abrirBoton.setToolTipText("Abrir archivo");
    this.abrirBoton.setEnabled(true);
    this.jtbar.add(this.abrirBoton);

    this.salvarBoton = new JButton(new ImageIcon(getClass().getResource("iconos/salvar.png")));

    this.salvarBoton.setToolTipText("Salvar archivo");
    this.salvarBoton.setEnabled(false);
    this.jtbar.add(this.salvarBoton);

    this.imprimirBoton = new JButton(new ImageIcon(getClass().getResource("iconos/imprimir.png")));

    this.imprimirBoton.setToolTipText("Imprimir archivo");
    this.imprimirBoton.setEnabled(false);
    this.jtbar.add(this.imprimirBoton);
    this.jtbar.addSeparator();

    this.cortarBoton = new JButton(new ImageIcon(getClass().getResource("iconos/cortar.png")));

    this.cortarBoton.setToolTipText("Cortar");
    this.cortarBoton.setEnabled(false);
    this.jtbar.add(this.cortarBoton);

    this.copiarBoton = new JButton(new ImageIcon(getClass().getResource("iconos/copiar.png")));

    this.copiarBoton.setToolTipText("Copiar texto");
    this.copiarBoton.setEnabled(false);
    this.jtbar.add(this.copiarBoton);

    this.pegarBoton = new JButton(new ImageIcon(getClass().getResource("iconos/pegar.png")));

    this.pegarBoton.setToolTipText("Pegar texto");
    this.pegarBoton.setEnabled(false);
    this.jtbar.add(this.pegarBoton);

    this.limpiarBoton = new JButton(new ImageIcon(getClass().getResource("iconos/limpiar.png")));

    this.limpiarBoton.setToolTipText("Limpiar Texto");
    this.limpiarBoton.setEnabled(false);
    this.jtbar.add(this.limpiarBoton);
    this.jtbar.addSeparator();

    this.undoBoton = new JButton(new ImageIcon(getClass().getResource("iconos/undo.png")));

    this.undoBoton.setToolTipText("Deshacer");
    this.undoBoton.setEnabled(false);
    this.jtbar.add(this.undoBoton);

    this.redoBoton = new JButton(new ImageIcon(getClass().getResource("iconos/redo.png")));

    this.redoBoton.setToolTipText("Rehacer");
    this.redoBoton.setEnabled(false);
    this.jtbar.add(this.redoBoton);

    this.buscarBoton = new JButton(new ImageIcon(getClass().getResource("iconos/buscar.png")));

    this.buscarBoton.setToolTipText("Buscar coincidencia");
    this.buscarBoton.setEnabled(false);
    this.jtbar.add(this.buscarBoton);

    this.buscarNextBoton = new JButton(new ImageIcon(getClass().getResource("iconos/siguiente.png")));

    this.buscarNextBoton.setToolTipText("Buscar siguiente coincidencia");
    this.buscarNextBoton.setEnabled(false);
    this.jtbar.add(this.buscarNextBoton);

    this.jtbar.setFloatable(false);

    this.nuevoBoton.addActionListener(this);
    this.abrirBoton.addActionListener(this);
    this.salvarBoton.addActionListener(this);
    this.imprimirBoton.addActionListener(this);
    this.cortarBoton.addActionListener(this);
    this.copiarBoton.addActionListener(this);
    this.pegarBoton.addActionListener(this);
    this.limpiarBoton.addActionListener(this);
    this.undoBoton.addActionListener(this.undoAction);
    this.redoBoton.addActionListener(this.redoAction);
    this.buscarBoton.addActionListener(this);
    this.buscarNextBoton.addActionListener(this);
  }

  public void addColores()
  {
    this.COLOR_FONDO = new JMenu("Color de Fondo");
    this.COLOR_TEXTO = new JMenu("Color de Texto");

    String[] colFondo = { "Amarillo", "Azul", "Blanco", "Cyan", "Gris", "Gris Claro", "Gris Oscuro", "Magenta", "Naranja", "Negro", "Rojo", "Rosa", "Verde" };

    String[] colTexto = { " Amarillo", " Azul", " Blanco", " Cyan", " Gris", " Gris Claro", " Gris Oscuro", " Magenta", " Naranja", " Negro", " Rojo", " Rosa", " Verde" };

    for (int i = 0; i <= 12; i++) {
      this.COLOR_FONDO.add(this.arrayColoresFon[i] =  = new JMenuItem(colFondo[i]));
      this.arrayColoresFon[i].addActionListener(this);
      this.COLOR_TEXTO.add(this.arrayColoresText[i] =  = new JMenuItem(colTexto[i]));
      this.arrayColoresText[i].addActionListener(this);
    }

    this.jmbar.add(this.COLOR_FONDO);
    this.jmbar.add(this.COLOR_TEXTO);
  }

  public void imprimir()
  {
    this.taImprimir = this.taArray[this.jtabbedPane.getSelectedIndex()];
    String str = this.taImprimir.getText();
    Properties propi = new Properties();
    Font fnt = new Font("Monoespaced", 0, 12);

    int margen = 15;

    StringReader strReader = new StringReader(str);
    LineNumberReader lineaNum = new LineNumberReader(strReader);

    PrintJob prjob = getToolkit().getPrintJob(this, "", propi);

    if (prjob != null) {
      Graphics gp = prjob.getGraphics();
      if (gp != null) {
        FontMetrics fntMetrics = gp.getFontMetrics(fnt);
        int alturaFuente = fntMetrics.getHeight();

        int descentFuente = fntMetrics.getDescent();

        int alturaCur = margen;

        int alturaPagina = prjob.getPageDimension().height - margen;

        gp.setFont(this.taArray[this.jtabbedPane.getSelectedIndex()].getFont());
        try {
          String nuevaLinea;
          do {
            nuevaLinea = lineaNum.readLine();
            if (nuevaLinea != null) {
              if (alturaCur + alturaFuente > alturaPagina) {
                gp.dispose();
                gp = prjob.getGraphics();
                alturaCur = margen;
              }
              alturaCur += alturaFuente;
              if (gp != null) {
                gp.setFont(fnt);

                gp.drawString(nuevaLinea, margen, alturaCur - descentFuente);
              }
            }
          }
          while (nuevaLinea != null);
        }
        catch (IOException io) {
          JOptionPane.showMessageDialog(null, "Excepción: Error al imprimir el archivo");
        }
      }

      gp.dispose();
    }
    if (prjob != null)
      prjob.end();
  }

  public void acercaDe()
  {
    JOptionPane.showMessageDialog(null, "\nEdiMax v1.0\n\nby Kalasni\n\nEmail:  kalassni@gmail.com    \n\n", "Acerca de", 1);
  }

  public static void main(String[] args)
  {
    Editor_Principal edi_prin = new Editor_Principal();
    edi_prin.setVisible(true);
  }

  class RedoAction extends AbstractAction
  {
    public RedoAction()
    {
      super();
      setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
      try {
        Editor_Principal.this.undo.redo();
      } catch (CannotRedoException ex) {
        System.out.println("No se ha podido rehacer: " + ex);
        ex.printStackTrace();
      }
      update();
      Editor_Principal.this.undoAction.update();
    }

    protected void update() {
      if (Editor_Principal.this.undo.canRedo()) {
        setEnabled(true);
        Editor_Principal.this.red().setEnabled(true);
        putValue("Name", Editor_Principal.this.undo.getRedoPresentationName());
      } else {
        setEnabled(false);
        Editor_Principal.this.red().setEnabled(false);
        putValue("Name", "Redo");
      }
    }
  }

  class UndoAction extends AbstractAction
  {
    public UndoAction()
    {
      super();
      setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
      try {
        Editor_Principal.this.undo.undo();
      } catch (CannotUndoException ex) {
        System.out.println("No se ha podido deshacer: " + ex);
        ex.printStackTrace();
      }
      update();
      Editor_Principal.this.redoAction.update();
    }

    protected void update() {
      if (Editor_Principal.this.undo.canUndo()) {
        setEnabled(true);
        Editor_Principal.this.und().setEnabled(true);
        putValue("Name", Editor_Principal.this.undo.getUndoPresentationName());
      } else {
        setEnabled(false);
        Editor_Principal.this.und().setEnabled(false);
        putValue("Name", "Undo");
      }
    }
  }

  class MiUndoableEditListener
    implements UndoableEditListener
  {
    MiUndoableEditListener()
    {
    }

    public void undoableEditHappened(UndoableEditEvent e)
    {
      Editor_Principal.this.undo.addEdit(e.getEdit());
      Editor_Principal.this.undoAction.update();
      Editor_Principal.this.redoAction.update();
    }
  }

  class JPopupListener extends MouseAdapter
  {
    JPopupListener()
    {
    }

    public void mousePressed(MouseEvent me)
    {
      muestraJpopup(me);
    }

    public void mouseReleased(MouseEvent me) {
      muestraJpopup(me);
    }

    public void muestraJpopup(MouseEvent me) {
      if (me.isPopupTrigger())
        Editor_Principal.this.jpopmenu.show(me.getComponent(), me.getX(), me.getY());
    }
  }

  protected class OyenteEtiquetaCursor extends JLabel
    implements CaretListener
  {
    public OyenteEtiquetaCursor(String label)
    {
      super();
    }

    public void caretUpdate(CaretEvent e) {
      int dot = e.getDot();
      int mark = e.getMark();

      if (dot == mark)
        try {
          int colum = dot - Editor_Principal.this.taArray[Editor_Principal.this.jtabbedPane.getSelectedIndex()].getLineStartOffset(Editor_Principal.this.taArray[Editor_Principal.this.jtabbedPane.getSelectedIndex()].getLineOfOffset(dot)) + 1;

          int fila = Editor_Principal.this.taArray[Editor_Principal.this.jtabbedPane.getSelectedIndex()].getLineOfOffset(dot) + 1;

          setText("Posición cursor: Col: " + colum + " Fila: " + fila);
        }
        catch (BadLocationException ble) {
          setText("Exception OyenteEtiquetaCursor");
        }
      else if (dot < mark)
        setText("Seleccionado desde: " + dot + " a " + mark);
      else
        setText("Seleccionado desde: " + mark + " a " + dot);
    }
  }
}