package view;

import javax.swing.*;
import java.awt.*;

public class testForm extends JFrame {
    private JPanel panel1;

    public testForm(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
    }



}
