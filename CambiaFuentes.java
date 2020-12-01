package edit;

/*
 * CambiaFuentes.java
 *
 * Created on 6 de mayo de 2006, 16:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * @author ocm128 | tdoc@tutamail.com
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class CambiaFuentes extends JDialog
{
  private JComboBox jcbfuentes;
  private JComboBox jcbtamano;
  private JLabel jlfuentes;
  private JLabel jltamano;
  private JLabel jlapariencia;
  private JLabel jlmuestra;
  public JButton okboton;
  public JButton cancelboton;
  private JRadioButton jrbnormal;
  private JRadioButton jrbitalic;
  private JRadioButton jrbbold;
  private JRadioButton jrbboldItalic;
  private ButtonGroup bgrupo;
  private JPanel jpcombox;
  private JPanel jpradiobotones;
  private Border borde1;
  private Border borde2;
  private Border borde3;
  private TitledBorder titledbordeBotones;
  private TitledBorder titledbordeCombox;
  private String[] arrayFuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

  private String[] arrayTamano = { "12", "14", "16", "18", "20", "22", "24", "28" };

  private String[] arrayEstilo = { "0", "1", "2", "3" };

  private int ind = 0;
  private Font fseleccionada;
  private OyenteJrbotones oyentejrbotones;
  private OyenteJcbfuentes oyentejcbfuentes;

  public CambiaFuentes()
  {
    setTitle("Selector de Fuentes");
    this.oyentejrbotones = new OyenteJrbotones();
    this.oyentejcbfuentes = new OyenteJcbfuentes();

    this.jcbfuentes = new JComboBox(this.arrayFuentes);
    this.jcbtamano = new JComboBox(this.arrayTamano);
    this.jlfuentes = new JLabel();
    this.jltamano = new JLabel();
    this.jlapariencia = new JLabel();
    this.jlmuestra = new JLabel();
    this.jpcombox = new JPanel();
    this.jrbnormal = new JRadioButton();
    this.jrbitalic = new JRadioButton();
    this.jrbbold = new JRadioButton();
    this.jrbboldItalic = new JRadioButton();
    this.jpcombox = new JPanel();
    this.jpradiobotones = new JPanel();
    this.okboton = new JButton("ACEPTAR");
    this.cancelboton = new JButton("CANCELAR");
    this.bgrupo = new ButtonGroup();
    try
    {
      addWidgets();
    } catch (Exception ex) {
      System.out.println("Excepción en addWidgets \n");
      System.out.println(ex);
      ex.printStackTrace();
    }
  }

  public void addWidgets()
  {
    getContentPane().setLayout(null);

    this.borde2 = BorderFactory.createBevelBorder(1, Color.white, Color.white, new Color(115, 114, 105), new Color(165, 163, 151));

    this.borde3 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));

    this.titledbordeCombox = new TitledBorder(this.borde3, "");
    this.jpcombox.setBorder(this.titledbordeCombox);
    this.jpcombox.setBounds(new Rectangle(5, 5, 220, 190));
    this.jpcombox.setLayout(null);

    this.jlfuentes.setText("Tipo:");
    this.jlfuentes.setBounds(new Rectangle(5, 25, 40, 25));
    this.jlfuentes.setHorizontalAlignment(0);

    this.jcbfuentes.setBounds(new Rectangle(70, 25, 140, 25));
    this.jcbfuentes.addActionListener(this.oyentejcbfuentes);

    this.jltamano.setText("Tamaño:");
    this.jltamano.setBounds(new Rectangle(5, 80, 60, 25));
    this.jltamano.setHorizontalAlignment(0);

    this.jcbtamano.setBounds(new Rectangle(70, 80, 140, 25));
    this.jcbtamano.addActionListener(this.oyentejcbfuentes);

    this.jlapariencia.setText("Apariencia:");
    this.jlapariencia.setBounds(new Rectangle(5, 140, 75, 25));
    this.jlapariencia.setHorizontalAlignment(0);

    this.jlmuestra.setText("ZzYyXxWw");
    this.jlmuestra.setBorder(this.borde2);
    this.jlmuestra.setBounds(new Rectangle(90, 140, 90, 30));
    this.jlmuestra.setHorizontalAlignment(0);

    this.jpcombox.add(this.jlfuentes, null);
    this.jpcombox.add(this.jcbfuentes, null);
    this.jpcombox.add(this.jltamano, null);
    this.jpcombox.add(this.jcbtamano, null);
    this.jpcombox.add(this.jlapariencia, null);
    this.jpcombox.add(this.jlmuestra, null);

    this.borde1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));

    this.jpradiobotones.setBorder(this.borde1);
    this.jpradiobotones.setBounds(new Rectangle(230, 5, 135, 190));
    this.jpradiobotones.setLayout(null);

    this.jrbnormal.setText("Normal");
    this.jrbnormal.setBounds(new Rectangle(10, 15, 120, 30));
    this.jrbnormal.setActionCommand("Normal");
    this.jrbnormal.addActionListener(this.oyentejrbotones);
    this.jrbnormal.setSelected(true);

    this.jrbbold.setText("Bold");
    this.jrbbold.setBounds(new Rectangle(10, 55, 120, 30));
    this.jrbbold.setActionCommand("Bold");
    this.jrbbold.addActionListener(this.oyentejrbotones);

    this.jrbitalic.setText("Italic");
    this.jrbitalic.setBounds(new Rectangle(10, 95, 120, 30));
    this.jrbitalic.setActionCommand("Italic");
    this.jrbitalic.addActionListener(this.oyentejrbotones);

    this.jrbboldItalic.setText("Bold Italic");
    this.jrbboldItalic.setBounds(new Rectangle(10, 135, 120, 30));
    this.jrbboldItalic.setActionCommand("Bold Italic");
    this.jrbboldItalic.addActionListener(this.oyentejrbotones);

    this.jpradiobotones.add(this.jrbnormal, null);
    this.jpradiobotones.add(this.jrbbold, null);
    this.jpradiobotones.add(this.jrbitalic, null);
    this.jpradiobotones.add(this.jrbboldItalic, null);

    this.bgrupo.add(this.jrbnormal);
    this.bgrupo.add(this.jrbbold);
    this.bgrupo.add(this.jrbitalic);
    this.bgrupo.add(this.jrbboldItalic);

    this.okboton.setBounds(new Rectangle(70, 210, 115, 25));
    this.okboton.setFont(new Font("Dialog", 1, 14));
    this.cancelboton.setBounds(new Rectangle(190, 210, 115, 25));
    this.cancelboton.setFont(new Font("Dialog", 1, 14));

    getContentPane().add(this.jpcombox, null);
    getContentPane().add(this.jpradiobotones, null);
    getContentPane().add(this.okboton, null);
    getContentPane().add(this.cancelboton, null);

    setSize(380, 290);
    setResizable(false);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  public Font devuelveFont()
  {
    String fuentes = this.jcbfuentes.getSelectedItem().toString();
    int tamanoFuente = Integer.parseInt(this.jcbtamano.getSelectedItem().toString());

    this.fseleccionada = new Font(fuentes, Integer.parseInt(this.arrayEstilo[this.ind]), tamanoFuente);

    return this.fseleccionada;
  }

  class OyenteJrbotones
    implements ActionListener
  {
    OyenteJrbotones()
    {
    }

    public void actionPerformed(ActionEvent e)
    {
      String estilo = e.getActionCommand();
      if (estilo.equals("Normal")) {
        CambiaFuentes.this.ind = 0;
      }
      else if (estilo.equals("Bold")) {
        CambiaFuentes.this.ind = 1;
      }
      else if (estilo.equals("Italic")) {
        CambiaFuentes.this.ind = 2;
      }
      else if (estilo.equals("Bold Italic")) {
        CambiaFuentes.this.ind = 3;
      }
      CambiaFuentes.this.jlmuestra.setFont(new Font(CambiaFuentes.this.jcbfuentes.getSelectedItem().toString(), Integer.parseInt(CambiaFuentes.this.arrayEstilo[CambiaFuentes.this.ind]), 14));
    }
  }

  class OyenteJcbfuentes
    implements ActionListener
  {
    OyenteJcbfuentes()
    {
    }

    public void actionPerformed(ActionEvent e)
    {
      Object fuente = e.getSource();
      if (fuente == CambiaFuentes.this.jcbfuentes)
        CambiaFuentes.this.jlmuestra.setFont(new Font(CambiaFuentes.this.jcbfuentes.getSelectedItem().toString(), Integer.parseInt(CambiaFuentes.this.arrayEstilo[CambiaFuentes.this.ind]), 14));
    }
  }
}