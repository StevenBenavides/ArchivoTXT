package pooparcialsteven;
import java.util.ArrayList;

// imports CSV
import java.io.FileWriter;
import java.io.IOException;

public class PooParcialSteven1 {

    public static void main(String[] args) {
        Secretario sec = new Secretario("Luis", "Martinez", "110999999", "Av. Pio Jaramillo", 6, "777-1234", "Oficina 2", 180, 12, 15);
        sec.calcularSalario();
        System.out.println(sec);

        Vendedor vend = new Vendedor("Maria", "Lopez", "1150489261", "Calle Paris", 4, "888-5678", "XYZ-789", "Honda", "Civic", "999-6789", "Zona Sur", 7, 25000, 1300);
        vend.calcularSalario();
        System.out.println(vend);

        ArrayList<Vendedor> listaVendedores = new ArrayList<>();
        listaVendedores.add(vend);
        JefeZona jefe = new JefeZona("Roberto", "Gonzalez", "110123456789", "Av. Isidro Ayora", 12, "666-9876", "Oficina Central", sec, listaVendedores, 400000);
        jefe.calcularSalario();
        System.out.println(jefe);
        
        // Guardar resultados en un archivo CSV
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados.add(sec);
        listaEmpleados.add(vend);
        listaEmpleados.add(jefe);
        
        try {
            escrituraArchivoCSV.escrituraCSV("empleados.csv", listaEmpleados);
            System.out.println("Archivo CSV creado.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

/*
Clase padre de la cual heredan el resto de clases
 */
abstract class Empleado {

    public String nombre;
    public String apellidos;
    public String cedula;
    public String direccion;
    public int aniosAntiguedad;
    public String telefono;
    protected double salario;
    
    /*
    Constructor principal del cual heredarán el resto de clases
    */

    public Empleado(String nombre, String apellidos, String cedula, String direccion, int aniosAntiguedad, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.direccion = direccion;
        this.aniosAntiguedad = aniosAntiguedad;
        this.telefono = telefono;
    }

    /*
    Funcion abstracta a utilizar para las subclases
     */
    public abstract void calcularSalario();
    
    // Reutilizamos la logica del toString para dar formato a la cadena CSV
    public String toCSV() {
        return nombre + "," + apellidos + "," + cedula + "," + direccion + "," + aniosAntiguedad + "," + telefono + "," + salario;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Apellidos: " + apellidos + ", Cedula: " + cedula
                + ", Dirección: " + direccion + ", Años de Antigüedad: " + aniosAntiguedad
                + ", Teléfono: " + telefono + ", Salario: " + salario;
    }
}

/*
Subclase "Secretario que hereda de Empleado"
 */
class Secretario extends Empleado {

    public String lugarDespacho;
    public double horasTrabajadas;
    public double costoHora;
    public double horasExtras;

    public Secretario(String nombre, String apellidos, String cedula, String direccion, int aniosAntiguedad, String telefono,
            String lugarDespacho, double horasTrabajadas, double costoHora, double horasExtras) {
        super(nombre, apellidos, cedula, direccion, aniosAntiguedad, telefono);
        this.lugarDespacho = lugarDespacho;
        this.horasTrabajadas = horasTrabajadas;
        this.costoHora = costoHora;
        this.horasExtras = horasExtras;
    }

    @Override
    public void calcularSalario() {
        salario = (horasTrabajadas * costoHora) + (horasExtras * costoHora * 1.5);
    }
    
    @Override
    public String toCSV() {
        return super.toCSV() + "," + lugarDespacho;
    }
    

    @Override
    public String toString() {
        return super.toString() + ", Despacho: " + lugarDespacho;
    }
}

/*
Subclase Vendedor
 */
class Vendedor extends Empleado {

    public String autoMatricula;
    public String autoMarca;
    public String autoModelo;
    public String telefonoMovil;
    public String areaVenta;
    public double porcentajeComision;
    public double ventasMensuales;
    public double salarioBase;

    public Vendedor(String nombre, String apellidos, String cedula, String direccion, int aniosAntiguedad, String telefono,
            String autoMatricula, String autoMarca, String autoModelo, String telefonoMovil, String areaVenta,
            double porcentajeComision, double ventasMensuales, double salarioBase) {
        super(nombre, apellidos, cedula, direccion, aniosAntiguedad, telefono);
        this.autoMatricula = autoMatricula;
        this.autoMarca = autoMarca;
        this.autoModelo = autoModelo;
        this.telefonoMovil = telefonoMovil;
        this.areaVenta = areaVenta;
        this.porcentajeComision = porcentajeComision;
        this.ventasMensuales = ventasMensuales;
        this.salarioBase = salarioBase;
    }

    @Override
    public void calcularSalario() {
        salario = salarioBase + (ventasMensuales * porcentajeComision / 100);
    }
    
    @Override
    public String toCSV() {
        return super.toCSV() + "," + autoMatricula + "," + autoMarca + "," + autoModelo + "," + telefonoMovil + "," + areaVenta;
    }

    @Override
    public String toString() {
        return super.toString() + ", Coche: " + autoMatricula + " " + autoMarca + " " + autoModelo
                + ", Teléfono Móvil: " + telefonoMovil + ", Área de Venta: " + areaVenta;
    }
}

/*
Subclase Jefe de Zona que hereda de Empleado
 */
class JefeZona extends Empleado {

    private String lugarDespacho;
    private Secretario secretario;
    private ArrayList<Vendedor> vendedores;
    private double ventasTotales;

    public JefeZona(String nombre, String apellidos, String cedula, String direccion, int aniosAntiguedad, String telefono,
            String lugarDespacho, Secretario secretario, ArrayList<Vendedor> vendedores, double ventasTotales) {
        super(nombre, apellidos, cedula, direccion, aniosAntiguedad, telefono);
        this.lugarDespacho = lugarDespacho;
        this.secretario = secretario;
        this.vendedores = vendedores;
        this.ventasTotales = ventasTotales;
    }

    @Override
    public void calcularSalario() {
        salario = ventasTotales * 0.1;
    }
    
    @Override
    public String toCSV() {
        return super.toCSV() + "," + lugarDespacho + "," + secretario.nombre + "," + vendedores.size();
    }

    @Override
    public String toString() {
        return super.toString() + ", Despacho: " + lugarDespacho + ", Secretario: " + secretario.nombre
                + ", Número de Vendedores a Cargo: " + vendedores.size();
    }
}
/*
Clase para CSV
*/
class escrituraArchivoCSV {

    public static void escrituraCSV(String filename, ArrayList<Empleado> empleados) throws IOException {
        try (FileWriter sc = new FileWriter(filename)) {
            // Encabezado de la tabla
            sc.append("Nombre,Apellidos,Cedula,Direccion,AnosAntiguedad,Telefono,Salario,Detalles\n");

            // Ciclo repetitivo for, para por cada iteracion
            // agregar la cadena con formato de cada subclase
            for (Empleado emp : empleados) {
                sc.append(emp.toCSV()).append("\n");
            }
        }
    }
}
