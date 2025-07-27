package geladeira.inteligente.MagicFrideAI.configur;

import geladeira.inteligente.MagicFrideAI.model.FooItem;
import geladeira.inteligente.MagicFrideAI.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class controller {
    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private GemininService geminiService;

    @GetMapping("/sugerir")
    public ResponseEntity<String> sugerirReceita() {
        List<FooItem> alimentos = foodItemRepository.findAll();
        String respostaIA = geminiService.sugerirReceita(alimentos);
        return ResponseEntity.ok(respostaIA);
    }
}
