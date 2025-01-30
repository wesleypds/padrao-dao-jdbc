package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.entities.Departamento;
import model.entities.Vendedor;

public class App {
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Departamento dep = new Departamento(1, "Cadernos");
        Vendedor vendedor;

        try {
            vendedor = new Vendedor(1, "Wesley Pereira", "wesley@email.com", sdf.parse("22/05/1997"), 2500.00, dep);
            System.err.println(vendedor);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
