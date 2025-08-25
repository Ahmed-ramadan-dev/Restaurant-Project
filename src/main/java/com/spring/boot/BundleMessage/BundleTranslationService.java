package com.spring.boot.BundleMessage;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
public class BundleTranslationService {
    private static ResourceBundleMessageSource messageSource;
    public BundleTranslationService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;

    }
    //لو انا عايز ال response يرجع ب العربي والانجلش
    public static BundleMessage getBundleMessageInEnglishAndArabic(String key){
        return new BundleMessage(
                messageSource.getMessage(key,null , new Locale("ar")),
                messageSource.getMessage(key,null , new Locale("en"))    );
    }
    /*
    //لو انا عايز ابعت اللغه في headers بتاع ال postman
    public static String getBundleMessage(String key){
        return messageSource.getMessage(key,null, LocaleContextHolder.getLocale());


    }*/

}
