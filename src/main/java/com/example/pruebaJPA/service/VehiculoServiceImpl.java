package com.example.pruebaJPA.service;

import com.example.pruebaJPA.dto.VehiculoDto;
import com.example.pruebaJPA.dto.VehiculoGetDto;
import com.example.pruebaJPA.dto.VehiculoResponseDto;
import com.example.pruebaJPA.entity.Vehiculo;
import com.example.pruebaJPA.exception.*;
import com.example.pruebaJPA.repository.IvehiculoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements IvehiculoService{

    IvehiculoRepository repository;

    public VehiculoServiceImpl(IvehiculoRepository vehiculoRepository) {
        this.repository = vehiculoRepository;
    }

    @Override
    public VehiculoResponseDto guardarVehiculo(VehiculoDto auto) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Verificar si existe el vehiculo si no existe lanza la excepción
        if(verificarSiExiste(auto)){
            throw new VehiculoClonException("El vehiculo ya existe");
        }
        Vehiculo vehiculo = mapper.convertValue(auto,Vehiculo.class);

        /*
        * Los objetos hijos no tienen forma de conocer su objeto principal en el contexto
        * de Hibernate por ello es que la FK se registra como NULL en la relación Bidireccional,
        * ya que, no se sabe a qué objeto debe apuntar o referenciar.
        * Para evitarlo debemos indicarle expresamente a Hibernate quien es su objeto padre,
        * como se realiza a continuación.
        *
        * Fuente:
        * https://stackoverflow.com/questions/52135048/foreign-key-is-always-null-in-one-to-many-relation-spring-boot-data-with-jpa
        *
        * https://stackoverflow.com/questions/58044640/jpa-inserting-foreign-key-as-null
         * */

        // Le seteamos al Service su objeto padre(vehiculo).
        vehiculo.getServices().forEach(service -> {
                service.setVehiculo(vehiculo);
        });

        // Guardamos el vehículo con el objeto padre(vehiculo) ya referenciado.
        Vehiculo respuestaRepo = repository.save(vehiculo);

        if(respuestaRepo == null){
            throw new VehiculoNoSaveException("Error!!! No se logró guardar el vehiculo.");
        }
        return new VehiculoResponseDto("El vehiculo modelo "+ respuestaRepo.getModel() + " se guardó correctamente.");
    }

    @Override
    public List<VehiculoGetDto> findAllSinServices() {
        List<Vehiculo> result = repository.findAll();

        // Se detiene el flujo del método si la lista está vacía y arroja una excepción
        if(result.isEmpty()){
            throw new VehiculoNotFoundException("No se encontraron vehículos");
        }
        return convertirDto(result);
    }

    /*
    * SOLUCIÓN ERROR => "Java 8 date/time type not supported by default"
    *
    * (El error se produce al querer serializar un LocalDate con la librería
    * default de ObjectMapper, dado que esta no reconoce las clases tipo TIME que
    * fueron incluidas en Java 8 por lo cual se hace necesario trabajar con una
    * versión actualizada de esta librería).
    *
    * Paso 1:
    *       Agregar la siguiente dependencia:
        *   <dependency>
                  <groupId>com.fasterxml.jackson.datatype</groupId>
                  <artifactId>jackson-datatype-jsr310</artifactId>
                  <version>2.13.4</version>
             </dependency>
    * Paso 2:
    *       Registrar el módulo JavaTimeModule() una vez inicializado el mapper, para
    *       indicar que vamos a trabajar con la versión actualizada y no con el default:
    *
    *       ObjectMapper mapper = new ObjectMapper();
    *       mapper.registerModule(new JavaTimeModule());
    *
    * Fuente:
    *
    * https://howtodoinjava.com/jackson/java-8-date-time-type-not-supported-by-default/
    * https://stackoverflow.com/questions/74188846/how-to-fix-jackson-java-8-data-time-error
    * */

    @Override
    public VehiculoDto findVehiculoById(int id)  {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Long idVehiculo = (long) id;
        /*
        Usar el Método findById en lugar del referentById para evitar el error
        en el mapeo que se da cuando se usa este último

        Fuente:
        https://stackoverflow.com/questions/52656517/no-serializer-found-for-class-org-hibernate-proxy-pojo-bytebuddy-bytebuddyinterc
         */

        // Verificar si existe el id si no existe lanza la excepción

        /*
        if(repository.existsById(idVehiculo)) {
            throw new VehiculoNotFoundIdException("No existen vehículos con este Id");
        }
        * */

        if(!verificarSiExisteId(id)){
            throw new VehiculoNotFoundIdException("No existen vehículos con este Id");
        }
        Vehiculo auto = repository.findById(idVehiculo).get();
        return mapper.convertValue(auto,VehiculoDto.class);
    }

   @Override
    public List<VehiculoGetDto> findVehiculosByDate(LocalDate date1, LocalDate date2) {
        List<Vehiculo> result = repository.findVehiculosByDateBetween(date1, date2);

       if(result.isEmpty()){
           throw new VehiculoNotFoundException("No se encontraron vehículos en el rango seleccionado");
       }
        return convertirDto(result);
    }

    /*
    // Usando un Dto sin Services

    @Override
    public List<VehiculoGetDto> findVehiculosByPrice(int price1, int price2) {
        List<Vehiculo> result = repository.findVehiculosByPrice(price1, price2);
        return convertirDto(result);
    }
    * */


    // Usando un dto con todos los atributos (Incluyendo Services)
    @Override
    public List<VehiculoDto> findVehiculosByPrice(int price1, int price2) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Vehiculo> result = repository.findVehiculosByPriceBetween(price1, price2);

        if(result.isEmpty()){
            throw new VehiculoNotFoundException("No se encontraron vehículos en el rango seleccionado");
        }
        return result.stream().map(v -> mapper.convertValue(v, VehiculoDto.class)).toList();
    }

    @Transactional
    @Override
    public VehiculoResponseDto modificarVehiculo(VehiculoDto vehiculoDto) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Vehiculo vehiculo = mapper.convertValue(vehiculoDto, Vehiculo.class);
        Optional<Vehiculo> encontrado = repository.findById(vehiculo.getId());

        if(encontrado.isPresent()) {
            Vehiculo modificado = encontrado.get();
            modificado.setBrand(vehiculo.getBrand());
            modificado.setModel(vehiculo.getModel());
            modificado.setManufacturingDate(vehiculo.getManufacturingDate());
            modificado.setDoors(vehiculo.getDoors());
            modificado.setPrice(vehiculo.getPrice());
            modificado.setNumberOfKilometers(vehiculo.getNumberOfKilometers());
            modificado.setCurrency(vehiculo.getCurrency());
            modificado.setCountOfOwners(vehiculo.getCountOfOwners());
            modificado.setServices(vehiculo.getServices());
        }else{
            throw new VehiculoNotFoundException("Vehículo inexistente");
        }
        return new VehiculoResponseDto("Vehículo modificado con éxito");
    }

    @Override
    public VehiculoResponseDto eliminarVehiculo(Long id) {
        Optional<Vehiculo> vehiculo = repository.findById(id);

        if(vehiculo.isPresent()) {
            repository.deleteById(id);
        }else{
            throw new VehiculoNotFoundException("Vehículo inexistente");
        }
        return new VehiculoResponseDto("El usuario fue eliminado con éxito");
    }

    /*
    // Método lambda para convertir a dto usando Foreach()

    private List<VehiculoGetDto> convertirDto(List<Vehiculo> lista){
        List<VehiculoGetDto> listaResponse = new ArrayList<>();
        lista.stream().forEach(v ->{
            listaResponse.add(new VehiculoGetDto(v.getId(), v.getBrand(),v.getModel(), v.getManufacturingDate(),
                    v.getNumberOfKilometers(), v.getDoors(), v.getPrice(), v.getCurrency(),
                    v.getCountOfOwners()));
        });
        return listaResponse;
    }
    * */

    // Método lambda para convertir a dto usando Map()
    private List<VehiculoGetDto> convertirDto(List<Vehiculo> lista){
        List<VehiculoGetDto> listaResponse = lista.stream().map(v -> new VehiculoGetDto(v.getId(), v.getBrand(),
                v.getModel(), v.getManufacturingDate(), v.getNumberOfKilometers(), v.getDoors(), v.getPrice(),
                v.getCurrency(), v.getCountOfOwners())).toList();
        return listaResponse;
    }

    // Método para convertir String en LocalDate
    private LocalDate convertirFecha(String date) {
        // Se da formato a la fecha
        DateTimeFormatter formato =  DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Se parsea el parámetro de tipo String a tipo LocalDate
        LocalDate fecha = LocalDate.parse(date, formato);
        return fecha;
    }

   /*
   // Método para convertir String en Date

   private Date convertirFecha(String date) {
        // Se da formato a la fecha
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            // Se parsea el parámetro de tipo String a tipo Date
            fecha = formato.parse(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date" + e.getMessage());
        }
        return fecha;
    }
    * */

    // Verifica si el vehículo recibido ya existe en la BD
    private boolean verificarSiExiste(VehiculoDto vehiculo){

        List<Vehiculo> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<Vehiculo> listaBusqueda = lista.stream()
                .filter(v -> v.getBrand().equals(vehiculo.getBrand())
                && v.getModel().equals(vehiculo.getModel())
                && v.getManufacturingDate().isEqual(vehiculo.getManufacturingDate()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }

    // Verifica si el ID recibido ya existe en la BD
    private boolean verificarSiExisteId(int id){

        List<Vehiculo> lista = repository.findAll();

        // si la lista que llega del repository esta vacía
        if(lista.isEmpty()){
            return false;
        }
        Vehiculo auto = lista.stream()
                .filter(v -> v.getId() == id).findFirst().orElse(null);

        // si el vehículo filtrado por ID es null es porque el id no existe en la BD
        if(auto == null){
            return false;
        }
        return true;
    }
}
