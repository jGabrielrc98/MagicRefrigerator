package geladeira.inteligente.MagicFrideAI.configur;

import geladeira.inteligente.MagicFrideAI.model.FooItem;
import geladeira.inteligente.MagicFrideAI.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // 👈 ESSENCIAL para o Spring reconhecer e injetar os @Autowired
@RequestMapping("/ia")
@CrossOrigin(origins = "http://localhost:3001") // 👈 CORS para o front-end funcionar
public class Controller {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private GemininService geminiService;

    @GetMapping("/sugerir")
    public ResponseEntity<String> sugerirReceita() {
        List<FooItem> alimentos = foodItemRepository.findAll();

        // Converte a lista de FooItem para uma lista de Strings com os nomes
        List<String> nomesDosAlimentos = alimentos.stream()
                .map(FooItem::getName) // <-- Certifique-se que "getNome" está correto
                .collect(Collectors.toList());

        String respostaIA = geminiService.sugerirReceita(nomesDosAlimentos);
        return ResponseEntity.ok(respostaIA);
    }
}
