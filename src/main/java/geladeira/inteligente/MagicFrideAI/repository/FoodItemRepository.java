package geladeira.inteligente.MagicFrideAI.repository;


import geladeira.inteligente.MagicFrideAI.model.FooItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FoodItemRepository extends JpaRepository<FooItem, Long> {
}
