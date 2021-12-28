import java.text.DecimalFormat;
import java.util.*;

public class MontyHall {
    public static void main(String[] args) {
        boolean cambiaPuerta = false; //Se cambiará la puerta
        int nPuertas = 100; //Puertas totales
        int abroPuertas = 1; //Cuantas puertas se abrirán
        int partidas = 1000000; //Cuantas partidas de jugarán
        boolean verbose = false; //Muestra el resultado de cada partida
        boolean interative = true; //Convierte el juego en interactivo (en este modo se preguntará el valor de todas las variables anteriores y el modo verbose quedará activado)
        int ganadas = 0; //Contador
        int perdidas = 0; //Contador
        Scanner scan = new Scanner(System.in);
        if (interative){ //Parámetros de configuración para el modo interactivo
            verbose = true;
            String sel = "";
            while (!sel.matches("\\d+")){
                System.out.print("Cuantas partidas desas jugar?: ");
                sel = scan.nextLine();
                if (!sel.matches("\\d+")){
                    System.out.println("Valor incorrecto!!");
                }else {
                    partidas = Integer.parseInt(sel);
                }
            }
            sel = "";
            while (!sel.matches("\\d+")){
                System.out.print("Cuantas puertas tendrá la partida?: ");
                sel = scan.nextLine();
                if (!sel.matches("\\d+")){
                    System.out.println("Valor incorrecto!!");
                }else {
                    nPuertas = Integer.parseInt(sel);
                }
            }
            sel = "";
            while (!sel.matches("\\d+")){
                System.out.print("Cuantas puertas abrirá el presentador?: ");
                sel = scan.nextLine();
                if (!sel.matches("\\d+")){
                    System.out.println("Valor incorrecto!!");
                }else {
                    abroPuertas = Integer.parseInt(sel);
                }
            }

        }

        if ((abroPuertas + 1) < nPuertas) { //Compruebo que los valores de puertas permitan una partida
            for (int i = 0; i < partidas; i++) { //Bucle de partidas jugadas
                if (verbose) System.out.println("Jugando partida " + (i+1) + " de " + partidas);
                ArrayList<Integer> puertas = new ArrayList<Integer>();
                for (int y = 0; y < nPuertas; y++) { //Creo la lista de puertas
                    puertas.add(y);
                }
                Random ran = new Random();
                Integer premio = puertas.get(ran.nextInt(nPuertas)); //Creo de forma aleatoria la puerta premiada
                if (verbose && !interative) System.out.println("Premio: " + premio);
                Integer seleccion = 0;
                if (interative) { //Modo interactivo para selección de la primera puerta
                    String sel = "";
                    while (!sel.matches("\\d+") || Integer.parseInt(sel) < 0 || Integer.parseInt(sel) >= puertas.size()){
                        System.out.print("Seleccione una puerta entre la 0 y la " + (puertas.size() - 1) + ": ");
                        sel = scan.nextLine();
                        if (!sel.matches("\\d+") || Integer.parseInt(sel) < 0 || Integer.parseInt(sel) >= puertas.size()){
                            System.out.println("Selección incorrecta!!");
                        }else {
                            seleccion = Integer.parseInt(sel);
                        }
                    }

                }else {
                    seleccion = puertas.get(ran.nextInt(nPuertas)); // Selecciono una puerta aleatoria
                }
                if (verbose) System.out.println("Selección: " + seleccion);
                puertas.remove(premio); //Elimino temporalmente la puerta premiada para asegurar mostrar una no premiada
                puertas.remove(seleccion); //Elimino la puerta seleccionada del listado
                for (int z = 0; z < abroPuertas; z++){ //Bucle para apertura de puertas
                    Integer muestra = puertas.get(ran.nextInt(puertas.size()));
                    if (verbose) System.out.println("Muestro: " + muestra);
                    puertas.remove(muestra); //Elimino la puerto abierta de la lista
                }
                if (seleccion != premio) { //Si la puerta seleccionada no es la premiada la reincorporo a la lista
                    puertas.add(premio);
                    puertas.sort(Comparator.naturalOrder());
 
                }
                if (cambiaPuerta && !interative) { //Selecciono otra puerta aleatoria
                    seleccion = puertas.get(ran.nextInt(puertas.size()));
                    if (verbose) System.out.println("Cambio a puerta: " + seleccion);
                }
                if (interative){ //Modo interactivo para reselección de puerta
                    String camb = "";
                    while (!camb.equalsIgnoreCase("s") && !camb.equalsIgnoreCase("n")){
                        System.out.print("Desea cambiar de puerta? (s/n): ");
                        camb = scan.nextLine();
                        if (camb.equalsIgnoreCase("s")){
                            cambiaPuerta = true;
                        }else if(camb.equalsIgnoreCase("n")){
                            cambiaPuerta = false;
                        }else {
                            System.out.println("Selección incorrecta!!");
                        }

                    }
                    if (cambiaPuerta) {
                        String nueva = "";
                        while (!nueva.matches("\\d+") || !puertas.contains(Integer.parseInt(nueva))) {
                            System.out.print("Seleccione una puerta entre las siguientes " + puertas.toString() + ": ");
                            nueva = scan.nextLine();
                            if (!nueva.matches("\\d+") || !puertas.contains(Integer.parseInt(nueva))){
                                System.out.println("Puerta incorrecta");
                            }else {
                                seleccion = Integer.parseInt(nueva);
                            }
                        }
                    }
                }
                if (seleccion == premio) {
                    if (verbose) System.out.println("HAS GANADO!!!");
                    ganadas++;
                } else {
                    if (verbose) System.out.println("HAS PERDIDO :(");
                    if (interative) System.out.println("La puerta premiada era la " + premio);
                    perdidas++;
                }
                if (verbose) System.out.println("-------------------------------");
            }
            DecimalFormat f = new DecimalFormat("0.00");
            System.out.println("Total partidas jugadas: " + partidas);
            System.out.println("Total puertas: " + nPuertas);
            System.out.println("Se mostr" +  (abroPuertas > 1?"aron ":"ó ") + abroPuertas + " puerta" + (abroPuertas > 1?"s.":"."));
            System.out.println(cambiaPuerta?"Se cambió de puerta en cada ronda.":"Se mantuvo la puerta inicial.");
            System.out.println("Total ganadas: " + ganadas + " (" + f.format((Double.valueOf(ganadas) / Double.valueOf(partidas)) * 100.0) + "%)");
            System.out.println("Total perdidas: " + perdidas + " (" + f.format((Double.valueOf(perdidas) / Double.valueOf(partidas)) * 100.0) + "%)");
        }else {
            System.out.println("Para un total de " + nPuertas + " el número de puertas abiertas tiene que ser menor a " + (nPuertas -1));
        }

    }
}
