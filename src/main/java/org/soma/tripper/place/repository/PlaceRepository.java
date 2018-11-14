package org.soma.tripper.place.repository;

import org.soma.tripper.place.dto.PlaceWithDistance;
import org.soma.tripper.place.entity.Place;
import org.soma.tripper.place.entity.PlaceThumb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place,Integer> {
    List<Place> findAll();
    Page<Place> findByNameContains(Pageable page, String name);

//    @Query(value = "select p.*, " + //p.place_num, p.name, p.thumbnum, p.city
//            " ( 6371 * acos( cos( radians(:averageLA) ) * cos( radians(p.latitude) ) * cos( radians( p.longtitude ) - radians(:averageLO) ) + sin( radians(:averageLA) ) * sin( radians( p.latitude ) ) ) ) AS distance" +
//            "from place p " +
//            "where where p.type =:version" ,nativeQuery = true)


    @Query(value = "select place.* , " +
            "( 6371 * acos( cos( radians(:averageLA) ) * cos( radians(place.latitude) ) * cos( radians( place.longtitude ) - radians(:averageLO) ) + sin( radians(:averageLA) ) * sin( radians( place.latitude ) ) ) )  as dis " +
            "from place " +
            "where place.type =:version " +
            "order by dis ",nativeQuery = true)
    List<Object[]> getPlaceByTypeaAndLongtitudeAndLatitude(@Param("version") int version,@Param("averageLA") double averageLA,@Param("averageLO") double averageLO,Pageable pageable);


    @Query(value = "select place.* , " +
            "( 6371 * acos( cos( radians(:averageLA) ) * cos( radians(place.latitude) ) * cos( radians( place.longtitude ) - radians(:averageLO) ) + sin( radians(:averageLA) ) * sin( radians( place.latitude ) ) ) )  as dis " +
            "from place " +
            "where place.type =:version " +
            "order by dis ",nativeQuery = true)
    List<Object> getCategory(@Param("version") int version,@Param("averageLA") double averageLA,@Param("averageLO") double averageLO,Pageable pageable);

    List<Place> getPlaceByThumb(PlaceThumb thumb);

}
