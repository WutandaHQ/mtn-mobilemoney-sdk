package com.wutanda.sdk.money.mobilemoney.mtn.utils;

import com.wutanda.sdk.money.mobilemoney.mtn.exceptions.SdkException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

public final class Xml {

    public static <T> String toXml(final T data) throws SdkException {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(data.getClass());
            final StringWriter stringWriter = new StringWriter();
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(data, stringWriter);
            return stringWriter.toString();
        } catch (Exception exception) {
            throw SdkException.internalError("Unable to parse data");
        }
    }

    public static <T> T fromXml(final String xmlData, final Class<T> tClass) throws SdkException {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            final StringReader stringReader = new StringReader(xmlData);
            return (T) unmarshaller.unmarshal(stringReader);
        } catch (Exception exception) {
            throw SdkException.internalError("Unable to parse data");
        }
    }

    public static <T> T fromXml(final InputStream inputStream, final Class<T> tClass) throws SdkException {
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T) unmarshaller.unmarshal(inputStream);
        } catch (Exception exception) {
            throw SdkException.internalError("Unable to parse data");
        }
    }


}