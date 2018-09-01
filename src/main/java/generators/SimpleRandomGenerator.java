package generators;

import lombok.Data;

import static org.apache.commons.lang3.RandomStringUtils.random;

@Data
public class SimpleRandomGenerator implements PassGenerator {

    private int passLength;
    private String password;
    private boolean numbers;

    @Override
    public String generate(boolean letters) {
        return random(passLength, letters, numbers);
    }
}
