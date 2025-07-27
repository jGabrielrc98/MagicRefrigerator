package geladeira.inteligente.MagicFrideAI.controller;


import geladeira.inteligente.MagicFrideAI.model.FooItem;
import geladeira.inteligente.MagicFrideAI.services.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin(origins = "http://localhost:3001")
public class FoodItemControler {

    private FoodItemService foodItemService;

    public FoodItemControler(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    //POST
    @PostMapping
    public ResponseEntity<FooItem> criar(@RequestBody FooItem fooItem) {
        FooItem salvo = foodItemService.salvar(fooItem);
        return ResponseEntity.ok(salvo);
    }

    //GET
    @GetMapping
    public ResponseEntity<List<FooItem>> listarTodos() {
        List<FooItem> lista = foodItemService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FooItem> buscarPorId(@PathVariable Long id) {
        FooItem encontrado = foodItemService.buscarPorId(id);
        return ResponseEntity.ok(encontrado);
    }


    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FooItem> atualizar(@PathVariable Long id, @RequestBody FooItem atualizado) {
        FooItem atualizadoFinal = foodItemService.atualizar(id, atualizado);
        return ResponseEntity.ok(atualizadoFinal);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        foodItemService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
