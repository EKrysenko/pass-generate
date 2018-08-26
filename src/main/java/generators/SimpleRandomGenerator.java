package generators;

import lombok.NoArgsConstructor;

import static org.apache.commons.lang3.RandomStringUtils.random;

@NoArgsConstructor
public class SimpleRandomGenerator implements PassGenerator {

    @Override
    public String generate(int length, boolean letters, boolean numbers) {
        return random(length, letters, numbers);
    }
}
