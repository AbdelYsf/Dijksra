import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Gui extends JFrame{


    // An object of Algorithm
    DijkstraAlgorithm m = new DijkstraAlgorithm();

    // Some useless stuff created by eclipse
    private static final long serialVersionUID = 1L;



    // Adjacency matrix (used to get minimum distance and path)
    int adjacency[][];

    // Some GUI components
    JPanel  upperPanel,lowerPanel, bottomPanel,  resultPanel , sidepanel,sidePanelContainer;
    JButton setDistance, calculate, start,  reset;
    JTextField fromTextfield, toTextfield, initialTextfield, finalTextfield, valueTextfield;
    JLabel fromLabel, toLabel, initialLabel, finalLabel, valueLabel, resultLabel;

    // Variable that will be used to name Nodes
    int count = 0;

    // ArrayList to store x-y co-ordinates of Nodes
    ArrayList<Integer> x_pos = new ArrayList<Integer>();
    ArrayList<Integer> y_pos = new ArrayList<Integer>();

    // Constructor
    public Gui(){

        setLayout(new BorderLayout());
        Font textFont = new Font("Book Antiqua", Font.PLAIN, 18);

        // Frame Icon's Image
        try{
            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("abc.png"));
            ImageIcon icon = new ImageIcon(image);
            setIconImage(icon.getImage());
        }catch(Exception e){}






        // Working Panel ( gray one )
        upperPanel = new JPanel();
        upperPanel.setBackground(new Color(20, 20, 20)); // Alternative (191, 201, 202)



        // result Panel
        resultLabel = new JLabel("le Resultat va s'afficher ici ... ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
        resultLabel.setForeground(Color.WHITE);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());

        resultPanel.setBackground(new Color(46, 182, 44));
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        // Blue Panel
        fromTextfield = new JTextField(2);
        toTextfield = new JTextField(2);
        initialTextfield = new JTextField(2);
        finalTextfield = new JTextField(2);
        valueTextfield = new JTextField(3);

        fromLabel = new JLabel("À partir de:", SwingConstants.CENTER);
        fromLabel.setFont(textFont);


        toLabel = new JLabel("À:", SwingConstants.CENTER);
        toLabel.setFont(textFont);
        initialLabel = new JLabel("Nœud inital:", SwingConstants.CENTER);
        initialLabel.setFont(textFont);
        finalLabel = new JLabel(" Nœud final:", SwingConstants.CENTER);
        finalLabel.setFont(textFont);
        valueLabel = new JLabel("cout:", SwingConstants.CENTER);
        valueLabel.setFont(textFont);

        setDistance = new JButton("Appliquer ");
        setDistance.setFont(new Font("Bell MT", Font.BOLD, 14));
        calculate = new JButton("Calculer");
        calculate.setFont(new Font("Bell MT", Font.BOLD, 14));
        start = new JButton("commençer");
        start.setFont(new Font("Bell MT", Font.BOLD, 14));
        reset = new JButton("réinitialiser");
        reset.setFont(new Font("Bell MT", Font.BOLD, 14));






        fromTextfield.setEnabled(false);
        toTextfield.setEnabled(false);
        valueTextfield.setEnabled(false);
        setDistance.setEnabled(false);
        initialTextfield.setEnabled(false);
        finalTextfield.setEnabled(false);
        calculate.setEnabled(false);

        lowerPanel = new JPanel();
        lowerPanel.setBackground(new Color(131, 212, 117));
        lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        lowerPanel.add(start);
        lowerPanel.add(fromLabel);
        lowerPanel.add(fromTextfield);
        lowerPanel.add(toLabel);
        lowerPanel.add(toTextfield);
        lowerPanel.add(valueLabel);
        lowerPanel.add(valueTextfield);
        lowerPanel.add(setDistance);
        lowerPanel.add(new JSeparator(SwingConstants.VERTICAL));


        // side panel
        sidepanel = new JPanel();
        sidepanel.setBackground(new Color(131, 212, 117));
        sidepanel.setLayout(new BoxLayout(sidepanel,BoxLayout.Y_AXIS));

        sidepanel.add(initialLabel);
        sidepanel.add(initialTextfield);
        sidepanel.add(finalLabel);
        sidepanel.add(finalTextfield);
        sidepanel.add(calculate);
        sidepanel.add(reset);

        // sidePanelContainer
        sidePanelContainer = new JPanel();
        sidePanelContainer.setBackground(new Color(20, 20, 20));
        sidePanelContainer.setLayout(new BorderLayout());
        sidePanelContainer.add(sidepanel,BorderLayout.SOUTH);



        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        bottomPanel.add(lowerPanel, BorderLayout.CENTER);
        bottomPanel.add(resultPanel, BorderLayout.NORTH);

        add(upperPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(sidePanelContainer,BorderLayout.EAST);


        // when you click Start button, this will happen ...
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                adjacency = new int[count][count];
                fromTextfield.setText("");
                toTextfield.setText("");
                valueTextfield.setText("");
                fromTextfield.requestFocus();
                start.setEnabled(false);
                fromTextfield.setEnabled(true);
                toTextfield.setEnabled(true);
                valueTextfield.setEnabled(true);
                setDistance.setEnabled(true);
                initialTextfield.setEnabled(true);
                finalTextfield.setEnabled(true);
                calculate.setEnabled(true);
            }
        });

        // when you hit enter in fromTextfield, control will move to toTextfield
        fromTextfield.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent text){
                toTextfield.requestFocus();
            }
        });

        // when you hit enter in toTextfield, control will move to valueTextfield
        toTextfield.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent text){
                valueTextfield.requestFocus();
            }
        });

        // when you hit enter in valueTextfield, control will click setDistance button
        valueTextfield.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent text){
                setDistance.doClick();
                fromTextfield.requestFocus();
                fromTextfield.setText("");
                toTextfield.setText("");
                valueTextfield.setText("");
            }
        });

        // It will draw a line b/w two nodes with the given value
        setDistance.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ee){
                try{
                    Integer fromInt = Integer.parseInt(fromTextfield.getText());
                    Integer toInt = Integer.parseInt(toTextfield.getText());
                    Integer value = Integer.parseInt(valueTextfield.getText());

                    // To check if user input a negative number or
                    // a number which is not yet have been assigned to any node
                    if(fromInt > count-1 || toInt > count-1 || value < 0 || fromInt < 0 || toInt < 0){
                        JOptionPane.showMessageDialog(null, "Entrée invalide", " Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                        // if user enters same number in both fields
                    }else if(fromInt == toInt){
                        JOptionPane.showMessageDialog(null, "Chemin en boucle est interdit", " Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    drawLine(fromInt, toInt, value);
                    fromTextfield.requestFocus();
                    fromTextfield.setText("");
                    toTextfield.setText("");
                    valueTextfield.setText("");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Entrée invalide", " Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // when you hit enter in initialTextfield, control will move to finalTextfield
        initialTextfield.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent text){
                finalTextfield.requestFocus();
            }
        });

        // when you hit enter in finalTextfield, control will click calculate button
        finalTextfield.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent text){
                calculate.doClick();
            }
        });

        // It will calculate the required distance, Dijkstra algorithm works here
        calculate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ee){

                try{
                    Integer source = Integer.parseInt(initialTextfield.getText());
                    Integer destination = Integer.parseInt(finalTextfield.getText());

                    // To check if user input a negative number or
                    // a number which is higher than the number of nodes
                    if (source > count-1 || destination > count-1 || source < 0 || destination < 0){
                        JOptionPane.showMessageDialog(null, "entrée invalide", " Erreur", JOptionPane.ERROR_MESSAGE);
                        initialTextfield.setText("");
                        finalTextfield.setText("");
                        initialTextfield.requestFocus();
                        return;
                    }

                    initialTextfield.setText("");
                    finalTextfield.setText("");

                    // main algorithm, m is the object of class Algorithm
                    m.Dijkstra(adjacency, source, destination);

                    // if there is no path b/w source and destination
                    if (m.distance == Integer.MAX_VALUE)
                        resultLabel.setText("y a pas de chmain entre " + source +" & " + destination);
                    else{
                        resultLabel.setText("le plus court chemain entre  " + source + " & " + destination + " est " + m.distance + " ( via" + m.via + " )");
                        showColor(m.path, source);
                    }

                    fromTextfield.setEnabled(false);
                    toTextfield.setEnabled(false);
                    valueTextfield.setEnabled(false);
                    setDistance.setEnabled(false);
                    initialTextfield.setEnabled(false);
                    finalTextfield.setEnabled(false);
                    calculate.setEnabled(false);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Invalid Input", " Error", JOptionPane.ERROR_MESSAGE);
                    initialTextfield.setText("");
                    finalTextfield.setText("");
                    initialTextfield.requestFocus();
                }

            }
        });

        // when you click on the g panel, this will happen ...
        upperPanel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(start.isEnabled()){
                    int flag = 0;

                    // to get the coordinate of the point where we clicked
                    int x = e.getX() - 6;
                    int y = e.getY() + 15;
                    x_pos.add(x + 2);
                    y_pos.add(y + 40);

                    // if you click on the same position more than once
                    for (int w=0; w<x_pos.size()-1; w++){
                        if(x+2 == x_pos.get(w) && y+40 == y_pos.get(w)){
                            JOptionPane.showMessageDialog(null, "Node Already present here", "Error", JOptionPane.ERROR_MESSAGE);
                            x_pos.remove(x_pos.size()-1);
                            y_pos.remove(y_pos.size()-1);
                            flag = 1;
                            return;
                        }
                    }
                    //System.out.println(x+","+y+","+count);
                    if(flag == 0)
                        drawNode(count++, x, y);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}

        });

        // reset button listener
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                count = 0;
                adjacency = new int[0][0];
                x_pos.clear();
                y_pos.clear();
                start.setEnabled(true);
                fromTextfield.setEnabled(false);
                toTextfield.setEnabled(false);
                valueTextfield.setEnabled(false);
                setDistance.setEnabled(false);
                initialTextfield.setEnabled(false);
                finalTextfield.setEnabled(false);
                calculate.setEnabled(false);
                resultLabel.setText("le Resultat va s'afficher ici...");
                m.reset();
                repaint();
            }
        });


    }

    // Method to change color of the required path (green color)
    private void showColor(ArrayList<Integer> path, int source){
        drawLineChangeColor(source, path.get(0));
        drawNode(source, x_pos.get(source)-2, y_pos.get(source)-41);
        int i;
        for (i=0; i<path.size()-1; i++){
            drawLineChangeColor(path.get(i), path.get(i+1));
            drawNode(path.get(i), x_pos.get(path.get(i))-2, y_pos.get(path.get(i))-41);
        }
        drawNode(path.get(i), x_pos.get(path.get(i))-2, y_pos.get(path.get(i))-41);


    }

    // Method to draw the node at the given coordinate
    private void drawNode(int count, int x, int y){
        Graphics g = this.getGraphics();
        Graphics2D graphics2d = (Graphics2D) g;
        g.setColor(new Color(87, 200, 77));
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.fillOval(x-13, y+10, 45, 45);
        Font font = new Font("Verdana", Font.PLAIN, 25);
        g.setFont(font);
        g.setColor(Color.WHITE);
        String text = count + "";
        if (count > 9)
            g.drawString(text, x-6, y+41);
        else
            g.drawString(text, x+2, y+41);
    }

    // Method to draw a line b/w two nodes with the given value as its weight
    private void drawLine(int from, int to, int value){
        if(adjacency[from][to] != 0){
            JOptionPane.showMessageDialog(null, "Can't Overwrite", " Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        adjacency[from][to] = value;
        Graphics g = this.getGraphics();
        Graphics2D graphics2d = (Graphics2D) g;
        g.setColor(new Color(111, 150, 255));
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawLine(x_pos.get(from)+4, y_pos.get(from)-4, x_pos.get(to)+4, y_pos.get(to)-4);
        String st = value + "";
        int x = (((x_pos.get(from) + x_pos.get(to))/2) + (x_pos.get(to)))/2;
        int y = (((y_pos.get(from) + y_pos.get(to))/2) + (y_pos.get(to)))/2;
        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(st, x, y);
        drawNode(from, x_pos.get(from)-2, y_pos.get(from)-41);
        drawNode(to, x_pos.get(to)-2, y_pos.get(to)-41);
    }

    // Re-draw the lines which has now green color
    private void drawLineChangeColor(int from, int to){
        Graphics g = this.getGraphics();
        Graphics2D graphics2d = (Graphics2D) g;
        g.setColor(new Color(0, 130, 0));
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawLine(x_pos.get(from)+4, y_pos.get(from)-4, x_pos.get(to)+4, y_pos.get(to)-4);
    }


}
