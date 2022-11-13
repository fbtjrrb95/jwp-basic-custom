package next.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ModelAndView {
    @Getter
    private final View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView addObject(String name, Object value) {
        model.put(name, value);
        return this;
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }
}
