    package org.soma.tripper.review.repository;

    import org.soma.tripper.review.entity.Review;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;


    public interface ReviewRepository extends JpaRepository<Review,Integer> {

        List<Review> findReviewByUsernumAndSchedulenum(int usernum,int schedulenum);
    }
