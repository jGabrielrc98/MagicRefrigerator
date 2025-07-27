package geladeira.inteligente.MagicFrideAI.services;


import geladeira.inteligente.MagicFrideAI.model.FooItem;
import geladeira.inteligente.MagicFrideAI.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public FooItem salvar(FooItem fooItem){
        return repository.save(fooItem);
    }

    public List<FooItem>listar(){
        return repository.findAll();
    }

    //Listar por ID
    public FooItem buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    // Atualizar item existente
    public FooItem atualizar(Long id, FooItem atualizado) {
        FooItem existente = buscarPorId(id); // Verifica se existe

        // Atualiza os campos (ajuste conforme os atributos da sua entidade)
        existente.setName(atualizado.getName());
        existente.setQuantidade(atualizado.getQuantidade());
        existente.setValidade(atualizado.getValidade());

        return repository.save(existente);
    }

    // Deletar por ID
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Item não encontrado para deletar com ID: " + id);
        }
        repository.deleteById(id);
    }
}



