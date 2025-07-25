import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Comment {
    String text;
    String time;

    Comment(String text, String time) {
        this.text = text;
        this.time = time;
    }

    public String toString() {
        return text + " (" + time + ")";
    }
}

class Route {
    String name;
    String status;
    List<Comment> comments;

    Route(String name, String status) {
        this.name = name;
        this.status = status;
        this.comments = new ArrayList<>();
    }

    void addComment(String text, String time) {
        comments.add(new Comment(text, time));
    }

    void showComments() {
        System.out.println(name + " [" + status + "]:");
        if (comments.isEmpty()) {
            System.out.println("  (Sin comentarios)");
        } else {
            for (Comment c : comments) {
                System.out.println("  - " + c);
            }
        }
    }

    public String toString() {
        return name + " [" + status + "]";
    }
}

class Contact {
    String name;
    boolean active;
    String duration;
    String message;

    Contact(String name) {
        this.name = name;
        this.active = false;
        this.duration = null;
        this.message = null;
    }

    void activate(String duration, String message) {
        this.active = true;
        this.duration = duration;
        this.message = message;
    }

    void deactivate() {
        this.active = false;
        this.duration = null;
        this.message = null;
    }

    public String toString() {
        if (active) {
            return name + " [🚨] (" + duration + ") - " + message;
        } else {
            return name;
        }
    }
}

public class AlertaAqp {
    static HashMap<String, String> usuarios = new HashMap<>();
    static List<Route> rutas = new ArrayList<>();
    static List<Contact> contactos = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String usuarioActual = null;

    static HashMap<String, String[]> serviciosPorDistrito = new HashMap<>();
    static {
        serviciosPorDistrito.put("Hunter", new String[]{"116", "054441172", "054440398, 959517897"});
        serviciosPorDistrito.put("Paucarpata", new String[]{"116", "054452047, 054762720, 054461824", "054718990"});
        serviciosPorDistrito.put("Sachaca", new String[]{"116", "106", "054752000, 966938130"});
        serviciosPorDistrito.put("Yanahuara", new String[]{"054253933", "106", "054568024, 991562868"});
        serviciosPorDistrito.put("Miraflores", new String[]{"054498430", "054214318", "054265544, 999906717"});
    }

    public static void main(String[] args) {

        Route ejercito = new Route("Av. Ejercito", "Ruta segura");
        ejercito.addComment("Pase por esta calle después de las 5pm y todo tranquilo", "19:00");
        Route independencia = new Route("Av. Independencia", "Peligroso");
        independencia.addComment("Zona con poca iluminación, evitar después de las 8pm", "21:00");
        Route salaverry = new Route("Salaverry", "Ruta segura");
        salaverry.addComment("Zona con buena iluminación y cámaras de vigilancia", "20:00");
        Route dolores = new Route("Av. Dolores", "Peligroso");
        dolores.addComment("Me robaron el celular por esa calle, no pasen solos por la noche", "19:00");
        rutas.add(ejercito);
        rutas.add(independencia);
        rutas.add(salaverry);
        rutas.add(dolores);

        contactos.add(new Contact("Mamá"));
        contactos.add(new Contact("Papá"));
        contactos.add(new Contact("Hermana"));
        contactos.add(new Contact("Mejor amiga"));

        mostrarBienvenida();
        while (true) {
            mostrarMenuInicial();
            int opcion = obtenerOpcion();
            switch (opcion) {
                case 1:
                    registrarse();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    System.out.println("👋 ¡Gracias por usar Alerta Arequipa!");
                    scanner.close();
                    return;
                default:
                    System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    static void mostrarBienvenida() {
        System.out.println("=====================================");
        System.out.println("   Bienvenido(a) a Alerta Arequipa   ");
        System.out.println("=====================================");
        System.out.println("Tu seguridad, nuestra prioridad.\n");
    }

    static void mostrarMenuInicial() {
        System.out.println("\n--- Menú Inicial ---");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar Sesión");
        System.out.println("3. Salir");
        System.out.print("Elige una opción (1-3): ");
    }

    static int obtenerOpcion() {
        int opcion = -1;
        while (opcion == -1) {
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, ingrese un número válido.");
                System.out.print("Elige una opción: ");
            }
        }
        return opcion;
    }

    static void registrarse() {
        System.out.println("\n--- Registro ---");
        System.out.print("Nombre de usuario: ");
        String usuario = scanner.nextLine().trim();
        if (usuario.isEmpty()) {
            System.out.println("⚠️ El usuario no puede estar vacío.");
        } else if (usuarios.containsKey(usuario)) {
            System.out.println("⚠️ Usuario ya existe.");
        } else {
            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine().trim();
            if (contrasena.isEmpty()) {
                System.out.println("⚠️ La contraseña no puede estar vacía.");
            } else {
                usuarios.put(usuario, contrasena);
                System.out.println("✅ Usuario registrado.");
            }
        }
    }

    static void iniciarSesion() {
        System.out.println("\n--- Iniciar Sesión ---");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();

        if (usuarios.containsKey(usuario)) {
            if (usuarios.get(usuario).equals(contrasena)) {
                usuarioActual = usuario;
                System.out.println("✅ Sesión iniciada como " + usuario);
                mostrarMenuPrincipal();
            } else {
                System.out.println("❌ Contraseña incorrecta.");
            }
        } else {
            System.out.println("❌ Usuario no encontrado.");
        }
    }

    static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Botón de Pánico");
            System.out.println("2. Rutas Seguras");
            System.out.println("3. Directorio de Emergencias");
            System.out.println("4. Modo Acompañamiento");
            System.out.println("5. Educación en Seguridad");
            System.out.println("6. Cerrar Sesión");
            System.out.print("Elige una opción (1-6): ");

            int opcion = obtenerOpcion();
            switch (opcion) {
                case 1:
                    System.out.println("🚨 Botón de Pánico activado.");
                    break;
                case 2:
                    manejarRutasSeguras();
                    break;
                case 3:
                    manejarDirectorioEmergencias();
                    break;
                case 4:
                    manejarModoAcompanamiento();
                    break;
                case 5:
                    manejarEducacionSeguridad();
                    break;
                case 6:
                    System.out.println("🔒 Sesión cerrada.");
                    usuarioActual = null;
                    return;
                default:
                    System.out.println("⚠️ Opción no válida.");
            }
        }
    }

    static void manejarRutasSeguras() {
        int opcion;
        do {
            System.out.println("\n--- Rutas Seguras ---");
            System.out.println("1. Mostrar comentarios de rutas");
            System.out.println("2. Añadir comentario");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = obtenerOpcion();

            if (opcion == 1) {
                System.out.println("\nComentarios por ruta:");
                for (int i = 0; i < rutas.size(); i++) {
                    rutas.get(i).showComments();
                }
            } else if (opcion == 2) {
                System.out.println("\nRutas disponibles:");
                for (int i = 0; i < rutas.size(); i++) {
                    System.out.println((i + 1) + ". " + rutas.get(i));
                }
                System.out.print("Seleccione la ruta (número): ");
                int idx = obtenerOpcion() - 1;
                if (idx >= 0 && idx < rutas.size()) {
                    System.out.print("Ingrese su comentario: ");
                    String comment = scanner.nextLine().trim();
                    if (comment.isEmpty()) {
                        System.out.println("⚠️ El comentario no puede estar vacío.");
                        continue;
                    }
                    System.out.print("Ingrese la hora (HH:mm): ");
                    String time = scanner.nextLine().trim();
                    if (time.isEmpty()) {
                        System.out.println("⚠️ La hora no puede estar vacía.");
                        continue;
                    }
                    rutas.get(idx).addComment(comment, time);
                    System.out.println("✅ Tu comentario ha sido publicado.");
                } else {
                    System.out.println("⚠️ Opción de ruta no válida.");
                }
            } else if (opcion == 3) {
                System.out.println("Volviendo al menú principal...");
            } else {
                System.out.println("⚠️ Opción no válida.");
            }
        } while (opcion != 3);
    }

    static void manejarDirectorioEmergencias() {
        System.out.println("\n--- Directorio de Emergencias ---");
        System.out.println("Distritos disponibles:");
        String[] distritos = serviciosPorDistrito.keySet().toArray(new String[0]);
        for (int i = 0; i < distritos.length; i++) {
            System.out.println((i + 1) + ". " + distritos[i]);
        }
        System.out.print("Seleccione un distrito (número): ");
        int idx = obtenerOpcion() - 1;
        if (idx >= 0 && idx < distritos.length) {
            String distrito = distritos[idx];
            String[] servicios = serviciosPorDistrito.get(distrito);
            System.out.println("\n=== Información para " + distrito + " ===");
            System.out.println("Bomberos: " + servicios[0]);
            System.out.println("Centro de Salud: " + servicios[1]);
            System.out.println("Serenazgo: " + servicios[2]);
        } else {
            System.out.println("⚠️ Opción no válida.");
        }
    }

    static void manejarModoAcompanamiento() {
        int opcion;
        do {
            System.out.println("\n--- Modo Acompañamiento ---");
            System.out.println("Contactos:");
            for (int i = 0; i < contactos.size(); i++) {
                System.out.println((i + 1) + ". " + contactos.get(i));
            }
            System.out.println("\nOpciones:");
            System.out.println("1. Activar ubicación en tiempo real");
            System.out.println("2. Desactivar la ubicación en tiempo real");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = obtenerOpcion();

            if (opcion == 1) {
                System.out.print("Seleccione contacto (número): ");
                int idx = obtenerOpcion() - 1;
                if (idx >= 0 && idx < contactos.size()) {
                    System.out.println("Duración de la ubicación:");
                    System.out.println("1. 15 minutos");
                    System.out.println("2. 1 hora");
                    System.out.println("3. 8 horas");
                    System.out.print("Seleccione duración: ");
                    int durOpt = obtenerOpcion();
                    String duration;
                    if (durOpt == 1) {
                        duration = "15 minutos";
                    } else if (durOpt == 2) {
                        duration = "1 hora";
                    } else if (durOpt == 3) {
                        duration = "8 horas";
                    } else {
                        System.out.println("⚠️ Opción no válida. Usando 15 minutos por defecto.");
                        duration = "15 minutos";
                    }
                    System.out.print("Ingrese el mensaje para el contacto: ");
                    String msg = scanner.nextLine().trim();
                    if (msg.isEmpty()) {
                        System.out.println("⚠️ El mensaje no puede estar vacío.");
                        continue;
                    }
                    for (int i = 0; i < contactos.size(); i++) {
                        contactos.get(i).deactivate();
                    }
                    contactos.get(idx).activate(duration, msg);
                    System.out.println("✅ Ubicación en tiempo real activada para " + contactos.get(idx).name + ".");
                } else {
                    System.out.println("⚠️ Contacto no válido.");
                }
            } else if (opcion == 2) {
                boolean found = false;
                for (int i = 0; i < contactos.size(); i++) {
                    if (contactos.get(i).active) {
                        contactos.get(i).deactivate();
                        System.out.println("✅ Ubicación en tiempo real desactivada para " + contactos.get(i).name + ".");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("⚠️ No hay ninguna ubicación activa.");
                }
            } else if (opcion == 3) {
                System.out.println("Volviendo al menú principal...");
            } else {
                System.out.println("⚠️ Opción no válida.");
            }
        } while (opcion != 3);
    }

    static void manejarEducacionSeguridad() {
        System.out.println("\n--- Educación en Seguridad ---");
        System.out.println("Consejos de seguridad:");
        System.out.println("- Evita caminar solo por zonas oscuras o poco transitadas.");
        System.out.println("- Mantén tus pertenencias cerca de ti en lugares públicos.");
        System.out.println("- Usa el Botón de Pánico en caso de emergencia para alertar a las autoridades.");
        System.out.println("- Comparte tu ubicación con un contacto de confianza al moverte por zonas desconocidas.");
        System.out.println("- Memoriza los números de emergencia: Bomberos (116), Ambulancia (106), Serenazgo (varía por distrito).");
    }
}
