import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Hangman extends Applet implements ActionListener
{
    //for screens
    Panel p_card;
    Panel p1 = new Panel (new GridLayout (1, 10));

    Panel card1, card2, card3;
    CardLayout cdLayout = new CardLayout ();

    //formating
    int width = 500, height = 500; //size of the screen
    int row = 3, col = 9; //the alphabets
    JLabel pics[] = new JLabel [row * col];
    String alp = "ABCDEFGHIJKLMONPQRSTUVWXYZ";
    Color backgroundcolor = Color.black;
    Border bord = BorderFactory.createLineBorder (Color.red);



    //game
    String word = "";
    char c;
    JLabel[] buz = new JLabel [10];
    char[] rep = new char [20];
    JLabel[] bomb = new JLabel [7];
    int r, scr = 0, score = 0, lives = 0, turn = 0;
    JTextField ww;
    JLabel solved;
    boolean corr = false, wrong = false;

    public void init ()
    { //initializing all the screens
	p_card = new Panel ();
	p_card.setLayout (cdLayout);

	resize (width, height);
	generate ();
	openingscreen ();
	instruction ();
	gamescreen ();


	setLayout (new BorderLayout ());
	add ("Center", p_card);
    }


    public void openingscreen ()
    {
	card1 = new Panel ();
	card1.setBackground (backgroundcolor);
	JButton back = new JButton (createImageIcon ("Bangman.png"));
	back.setPreferredSize (new Dimension (width, height));
	back.addActionListener (this);
	back.setActionCommand ("2");
	card1.add (back);
	p_card.add ("1", card1);
    }


    public void instruction ()
    { // instructions
	card2 = new Panel ();
	card2.setBackground (backgroundcolor);

	JLabel inst = new JLabel (createImageIcon ("Instructions.png"));
	card2.add (inst);

	JButton begin = new JButton ("Begin!");
	buttonfont (begin);
	begin.setPreferredSize (new Dimension (width, 50));
	begin.setActionCommand ("3");
	card2.add (begin);
	p_card.add ("2", card2);
    }


    public void gamescreen ()
    { //Main game screen
	card3 = new Panel ();
	card3.setBackground (backgroundcolor);

	JLabel title = new JLabel ("BANGMAN");
	titlefont (title);
	card3.add (title);

	//buttons

	JButton gen = new JButton ("Generate Word");
	buttonfont (gen);
	gen.setPreferredSize (new Dimension (300, 50));
	gen.setActionCommand ("gen");



	//the letters

	for (int i = 0 ; i < buz.length ; i++)
	{
	    buz [i] = new JLabel ("_");
	    buz [i].setPreferredSize (new Dimension (width / buz.length, 30));
	    buz [i].setForeground (Color.red);
	    p1.add (buz [i]);

	}
	if (word.length () < buz.length)
	{
	    for (int i = word.length () ; i < buz.length ; i++)
	    {
		buz [i].setText ("");
	    }
	}


	//user input
	Panel p = new Panel (new GridLayout (1, 3));
	JLabel wa = new JLabel ("Enter an alphabet");
	wa.setFont (new Font ("serif", Font.PLAIN, 23));
	wa.setForeground (Color.red);
	p.add (wa);
	ww = new JTextField ("", 5);
	p.add (ww);
	JButton ent = new JButton ("Enter");
	buttonfont (ent);
	ent.setActionCommand ("enter");
	p.add (ent);

	// available alphabets
	JLabel ava = new JLabel ("Available Characters");
	titlefont (ava);

	Panel p2 = new Panel (new GridLayout (row, col));
	for (int i = 0 ; i < 26 ; i++)
	{
	    pics [i] = new JLabel ("" + alp.charAt (i));
	    pics [i].setPreferredSize (new Dimension (50, 20));

	    pics [i].setForeground (Color.red);

	    p2.add (pics [i]);
	}
	Panel p3 = new Panel (new GridLayout (1, 7));
	for (int i = 0 ; i < bomb.length ; i++)
	{
	    bomb [i] = new JLabel (createImageIcon ("bomb.png"));
	    p3.add (bomb [i]);
	}

	solved = new JLabel ("Puzzles Solved: " + score);
	solved.setFont (new Font ("serif", Font.PLAIN, 30));
	solved.setForeground (Color.red);

	JButton inst = new JButton ("Instructions");
	buttonfont (inst);
	inst.setActionCommand ("2");

	for (int i = 0 ; i < rep.length ; i++) //check for letters
	    rep [i] = '/';

	card3.add (gen);
	card3.add (p);
	card3.add (p1);
	card3.add (ava);
	card3.add (p2);
	card3.add (p3);
	card3.add (solved);
	card3.add (inst);
	showStatus (word + " " + score);
	p_card.add ("3", card3);



    }


    public void generate ()
    { //generating a random word
	r = (int) (Math.random () * 11);
	if (r == 0)
	    word = "ENCRYPTION";
	else if (r == 1)
	    word = "ADMISSION";
	else if (r == 2)
	    word = "RECYCLE";
	else if (r == 3)
	    word = "ACROBATIC";
	else if (r == 4)
	    word = "JUNGLE";
	else if (r == 5)
	    word = "BACKGROUND";
	else if (r == 6)
	    word = "ADVENTURE";
	else if (r == 7)
	    word = "GEOGRAPHY";
	else if (r == 8)
	    word = "SUNLIGHT";
	else if (r == 9)
	    word = "VENGEANCE";
	else if (r == 10)
	    word = "TUESDAY";

	scr = 0;
    }


    public void check ()
    {
	for (int i = 0 ; i < word.length () ; i++)
	{
	    if (rep [i] == c)
	    {
		JOptionPane.showMessageDialog (null, "Invalid Character");
	    }
	    else if (word.charAt (i) == c) //right answer
	    {
		corr = true;
		buz [i].setText ("" + c);
		scr++;
	    }
	    else if (word.charAt (i) != c) //wrong answer
	    {
		wrong = false;
		if (corr == true)
		{
		    corr = true;
		}
		else
		    corr = false;

	    }
	}
	if (corr == true)
	{
	}
	else if (wrong == false)
	{
	    bomb [lives].setIcon (new ImageIcon ("fire.png"));
	    lives++;
	}

	for (int j = 0 ; j < 26 ; j++)
	{
	    char t = pics [j].getText ().charAt (0);
	    if (t == c)
	    {
		pics [j].setText ("_");
	    }
	}
	corr = false;
	wrong = true;
	rep [turn] = c;
	win ();
    }


    public void reword ()
    {
	for (int i = 0 ; i < word.length () ; i++) //new word
	{
	    buz [i].setText ("_");
	    buz [i].setPreferredSize (new Dimension (width / word.length (), width / word.length ()));
	}
	for (int i = word.length () ; i < buz.length ; i++)
	{
	    buz [i].setText ("");
	}
	for (int i = 0 ; i < 26 ; i++) //resotring alphabets
	{
	    pics [i].setText ("" + alp.charAt (i));
	}
	lives = 0;
	corr = false;
	wrong = true;
	turn = 0;
	for (int i = 0 ; i < bomb.length ; i++)
	{
	    bomb [i].setIcon (createImageIcon ("bomb.png"));
	}
    }


    public void win ()
    {
	if (scr == word.length ())
	{
	    JOptionPane.showMessageDialog (null, "Press generate for the next puzzle");
	    score++;
	    lives = 0;
	    turn = 0;
	    for (int i = 0 ; i < rep.length ; i++)
		rep [i] = '/';
	}
	else if (lives == 7)
	{
	    JOptionPane.showMessageDialog (null, "The room is on fire! You must click generate to restart!");
	    lives = 0;
	    score = 0;
	    for (int i = 0 ; i < rep.length ; i++)
		rep [i] = '/';

	}
	if (score == 10)
	    JOptionPane.showMessageDialog (null, "Congratulations, you escaped the room!");
	solved.setText ("Puzzles Solved: " + score);
    }


    //




    public void actionPerformed (ActionEvent e)
    {
	if (e.getActionCommand ().equals ("enter"))
	{
	    String temp = ww.getText ();
	    temp = temp.toUpperCase ();

	    if (temp.length () > 1)
		JOptionPane.showMessageDialog (null, "Error, you can only add one character");
	    else
	    {
		c = temp.charAt (0);
		turn++;
		check ();

	    }

	}
	else if (e.getActionCommand ().equals ("gen"))
	{
	    generate ();
	    reword ();
	}
	else if (e.getActionCommand ().equals ("2"))
	{
	    cdLayout.show (p_card, "2");
	}
	else if (e.getActionCommand ().equals ("3"))
	{
	    cdLayout.show (p_card, "3");
	}
	showStatus (word + " " + score + " " + scr);


    }


    public void titlefont (JLabel b)
    {
	b.setFont (new Font ("serif", Font.PLAIN, 50));
	b.setForeground (Color.red);
    }


    public void buttonfont (JButton b)
    {
	b.setForeground (Color.red);
	b.setBackground (Color.black);
	b.setBorder (bord);
	b.setFont (new Font ("serif", Font.PLAIN, 30));
	b.addActionListener (this);
    }


    protected static ImageIcon createImageIcon (String path)
    {
	java.net.URL imgURL = Hangman.class.getResource (path);
	if (imgURL != null)
	    return new ImageIcon (imgURL);
	else
	    return null;
    }
}


