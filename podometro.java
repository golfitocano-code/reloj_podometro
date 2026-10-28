/** 
 * Modela un podómetro que registra los pasos, la distancia y el tiempo
 * caminado durante una semana.
 *
 * @author Aránzazu Cano Muñiz
 */
public class Podometro{
    private static final char HOMBRE = 'H';
    private static final char MUJER = 'M';

    private static final double RAZON_ZANCADA_HOMBRE = 0.45;
    private static final double RAZON_ZANCADA_MUJER = 0.41;

    private static final int SABADO = 6;
    private static final int DOMINGO = 7;

    private String marca;
    private int altura;
    private char sexo;
    private int longitudZancada;
    private int totalPasosLaborables;
    private int totalPasosSabado;
    private int totalPasosDomingo;
    private double totalDistanciaSemana;
    private double totalDistanciaFinSemana;
    private int tiempo;
    private int caminatasNoche;

    /**
     * Crea un podómetro de la marca indicada.
     * El resto de los atributos se inicializan a cero, salvo el sexo.
     */
    public Podometro(String queMarca)
    {
        marca = queMarca;
        
    }

    /**
     * Devuelve la marca del podómetro.
     */
    public String getMarca()
    {
        return marca;
    }

    /**
     * Configura la altura y el sexo y calcula la longitud de zancada.
     */
    public void configurar(int queAltura, char queSexo)
    {
        altura = queAltura;
        sexo = queSexo;

        if (sexo == HOMBRE) {
            longitudZancada = (int) Math.ceil(altura * RAZON_ZANCADA_HOMBRE);
        }
        else {
            longitudZancada = (int) Math.floor(altura * RAZON_ZANCADA_MUJER);
        }
    }

    /**
     * Registra una caminata y actualiza pasos, distancia, tiempo y caminatas nocturnas.
     * Las horas se reciben en formato HHMM.
     */
    public void registrarCaminata(int pasos, int dia, int horaInicio, int horaFin)
    {
        int minutosInicio = (horaInicio / 100) * 60 + horaInicio % 100;
        int minutosFin = (horaFin / 100) * 60 + horaFin % 100;
        int duracion = minutosFin - minutosInicio;
        double distancia = pasos * longitudZancada / 100000.0;

        totalDistanciaSemana += distancia;
        tiempo += duracion;

        if (horaInicio / 100 >= 21) {
            caminatasNoche++;
        }

        switch (dia) {
            case SABADO:
                totalPasosSabado += pasos;
                totalDistanciaFinSemana += distancia;
                break;
            case DOMINGO:
                totalPasosDomingo += pasos;
                totalDistanciaFinSemana += distancia;
                break;
            default:
                totalPasosLaborables += pasos;
                break;
        }
    }
/**
     * Muestra la configuración actual del podómetro.
     */
    public void printConfiguración()
    {
        System.out.println("Configuración del podómetro");
        System.out.println("********************************");
        System.out.println("Altura: " + (altura / 100.0) + " metros");

        if (sexo == HOMBRE) {
            System.out.println("Sexo: HOMBRE");
        }
        else {
            System.out.println("Sexo: MUJER");
        }

        System.out.println("Longitud zancada: " + (longitudZancada / 100.0) + " metros");
        System.out.println();
    }

    /**
     * Muestra las estadísticas acumuladas durante la semana.
     */
    public void printEstadísticas()
    {
        System.out.println("Estadísticas");
        System.out.println("********************************");
        System.out.println("Distancia recorrida toda la semana: " + totalDistanciaSemana + " Km");
        System.out.println("Distancia recorrida fin de semana: " + totalDistanciaFinSemana + " Km");
        System.out.println();
        System.out.println("Nº pasos días laborables: " + totalPasosLaborables);
        System.out.println("Nº pasos SÁBADO: " + totalPasosSabado);
        System.out.println("Nº pasos DOMINGO: " + totalPasosDomingo);
        System.out.println();
        System.out.println("Nº caminatas realizadas a partir de las 21h.: " + caminatasNoche);
        System.out.println();
        System.out.println("Tiempo total caminado en la semana: "
                + (tiempo / 60) + "h. y " + (tiempo % 60) + "m.");
    }

    /**
     * Devuelve el periodo o los periodos con mayor número de pasos.
     */
    public String diaMayorNumeroPasos()
    {
        int maximo = Math.max(totalPasosLaborables,
                Math.max(totalPasosSabado, totalPasosDomingo));
        String resultado = "";

        if (totalPasosLaborables == maximo) {
            resultado = "LABORABLES";
        }
        if (totalPasosSabado == maximo) {
            if (!resultado.isEmpty()) {
                resultado += " ";
            }
            resultado += "SÁBADO";
        }
        if (totalPasosDomingo == maximo) {
            if (!resultado.isEmpty()) {
                resultado += " ";
            }
            resultado += "DOMINGO";
        }

        return resultado;
    }

    /**
     * Restablece las estadísticas y la configuración inicial, sin cambiar la marca.
     */
    public void reset()
    {
        altura = 0;
        sexo = MUJER;
        longitudZancada = 0;
        totalPasosLaborables = 0;
        totalPasosSabado = 0;
        totalPasosDomingo = 0;
        totalDistanciaSemana = 0.0;
        totalDistanciaFinSemana = 0.0;
        tiempo = 0;
        caminatasNoche = 0;
    }

}
