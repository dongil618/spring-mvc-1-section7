package hello.itemservice.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.dto.ItemParamDto;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // @PostMapping("/add")
    public String save(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        Item savedItem = itemRepository.save(item);

        model.addAttribute("item", savedItem);

        return "basic/item";
    }

    // @PostMapping("/add")
    public String saveV2(@ModelAttribute("item") Item item, Model model) {

        Item savedItem = itemRepository.save(item);

        // model.addAttribute("item", savedItem);   // 자동 추가, 생략 가능

        return "basic/item";
    }

    /**
     * @ModelAttribute의 name 속성 생략 가능
     * 생략할 시 Class의 앞 글자 소문자로 이름이 자동 생성 ex) HelloData -> helloData, Item -> item ...
     */
    // @PostMapping("/add")
    public String saveV3(@ModelAttribute Item item) {

        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/add")
    public String saveV4(Item item) {

        itemRepository.save(item);

        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute Item item) {

        ItemParamDto updateParam = ItemParamDto.builder()
                .itemName(item.getItemName())
                .price(item.getPrice())
                .quantity(item.getQuantity()).build();

        itemRepository.update(itemId, updateParam);

        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }
}
