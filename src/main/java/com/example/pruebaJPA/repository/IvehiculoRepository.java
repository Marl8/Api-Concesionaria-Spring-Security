package com.example.pruebaJPA.repository;

import com.example.pruebaJPA.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IvehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Usando consulta SQL
   /* @Query(value = "SELECT * FROM vehículos WHERE manufacturing_date > ?1 AND manufacturing_date <= ?2",
            nativeQuery = true)*/

    // Usando consulta JPQL
    //@Query("SELECT v FROM Vehiculo v WHERE v.manufacturingDate > ?1 AND v.manufacturingDate <= ?2")

    //Usando HQL
    @Query("FROM Vehiculo v WHERE v.manufacturingDate > ?1 AND v.manufacturingDate <= ?2")
    public List<Vehiculo> findVehiculosByDateBetween(LocalDate date1, LocalDate date2);

    public List<Vehiculo> findVehiculosByPriceBetween(int price1, int price2);
}
