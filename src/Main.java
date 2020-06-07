import services.CompanyService;
import view.MainForm;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        CompanyService companyService = CompanyService.getCompanyServiceInstance();

        MainForm mainForm = new MainForm("Tickets Selling", companyService);
        mainForm.setMinimumSize(new Dimension(1050,690));
        mainForm.setSize(1050,690);
        mainForm.setVisible(true);
    }

}
