package ru.klukva.naumenproject.services;

import com.ritaja.xchangerate.api.CurrencyConverter;
import com.ritaja.xchangerate.api.CurrencyConverterBuilder;
import com.ritaja.xchangerate.util.Currency;
import com.ritaja.xchangerate.util.Strategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ConvertorService {
    private final CurrencyConverter converter = new CurrencyConverterBuilder()
            .strategy(Strategy.CURRENCY_LAYER_FILESTORE)
            .accessKey("91534d2dacf8e07fe5036fd34ff15eea")
            .buildConverter();
    public double getConvertedValue(Double value, String inputCurrency, String outputCurrency) {
        try {
            converter.updateResource(Currency.RUB, Currency.USD);
            return converter
                    .convertCurrency(
                            BigDecimal.valueOf(value),
                            Currency.valueOf(inputCurrency),
                            Currency.valueOf(outputCurrency))
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Чел ты");
    }
}
