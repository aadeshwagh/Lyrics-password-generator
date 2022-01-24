package i0.aadeshwagh.musicpasswordgenarator.Controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import i0.aadeshwagh.musicpasswordgenarator.Entity.Rule1;
import i0.aadeshwagh.musicpasswordgenarator.Entity.Rule2;
import i0.aadeshwagh.musicpasswordgenarator.Entity.Rule3;
import i0.aadeshwagh.musicpasswordgenarator.Entity.Rule4;
import i0.aadeshwagh.musicpasswordgenarator.Entity.track;
import reactor.core.publisher.Mono;

@Controller
public class RequestController {

    String apiKey = "<>";
    static final String host = "lyrics-finder1.p.rapidapi.com";

    List<String> songLines;

    private final WebClient webClient;

    public RequestController() {
        webClient = WebClient.builder()
                .baseUrl("https://lyrics-finder1.p.rapidapi.com/")
                .defaultHeader("x-rapidapi-host", host)
                .defaultHeader("x-rapidapi-key", apiKey)
                .build();

    }

    @GetMapping("/")
    public String inputNames() {
        return "generate-password";
    }

    @PostMapping("/getsongbyname")
    public String showLyrics(@RequestBody MultiValueMap<String, String> formData, Model model) {
        String songName = formData.getFirst("songName").replace(" ", "-");
        String artistName = formData.getFirst("artistName").replace(" ", "-");

        try {
            Mono<track[]> resultsMono = this.webClient.get()
                    .uri("{artistName}/{songName}", artistName, songName)
                    .retrieve()
                    .bodyToMono(track[].class)
                    .timeout(Duration.ofSeconds(5));
            track[] result = resultsMono.block();
            List<track> tracks = Arrays.stream(result).collect(Collectors.toList());
            String data = tracks.get(0).getSongLyric();
            data = data.replace(",", "");
            String phares[] = data.split("(?=[A-Z])");
            List<String> ph = new ArrayList<>();
            for (String s : phares) {
                if (s.length() > 16 && s.length() <= 100) {
                    ph.add(s);
                }
            }

            songLines = ph.stream().collect(Collectors.toList());

            model.addAttribute("songLines", songLines);

        } catch (Exception e) {
            model.addAttribute("error", "error");
        }

        return "generate-password";
    }

    @PostMapping("/generatepassword")
    public String generatePassword(@RequestBody MultiValueMap<String, String> formData, Model model) {
        String passwordLine = formData.getFirst("passwordLine");
        String rule1 = formData.getFirst("rule1");
        String rule2 = formData.getFirst("rule2");
        String rule3 = formData.getFirst("rule3");
        String rule4 = formData.getFirst("rule4");

        if (rule2 != null) {
            Rule2 r2 = new Rule2();
            passwordLine = r2.operation(passwordLine);
        }
        if (rule3 != null) {
            Rule3 r3 = new Rule3();
            passwordLine = r3.operation(passwordLine);
        }
        if (rule4 != null) {
            Rule4 r4 = new Rule4();
            passwordLine = r4.operation(passwordLine);
        }
        if (rule1 != null) {
            Rule1 r1 = new Rule1();
            passwordLine = r1.operation(passwordLine);
        }

        model.addAttribute("password", passwordLine);
        model.addAttribute("songLines", songLines);

        return "generate-password";
    }

}
