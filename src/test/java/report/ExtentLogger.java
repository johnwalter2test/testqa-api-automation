package report;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;

import java.util.Map;

public class ExtentLogger {

    // Log request details: method, fullUrl, headers, body (if any)
    public static void logRequest(String method, String fullUrl, Object body, Map<String, String> headers) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test == null) return;

        // Log metadata as plain text: method + full URL
        StringBuilder meta = new StringBuilder();
        meta.append("Request Method: ").append(method).append("\n");
        meta.append("Request URI: ").append(fullUrl).append("\n");

        // Log headers if present
        if (headers != null && !headers.isEmpty()) {
            meta.append("Request Headers:\n");
            for (Map.Entry<String, String> e : headers.entrySet()) {
                meta.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
            }
        }

        test.info(meta.toString());

        // If body exists and looks like JSON, log it as a pure JSON code block
        if (body != null) {
            String bodyStr = toJsonString(body);
            test.info(MarkupHelper.createCodeBlock(bodyStr, CodeLanguage.JSON));
        }
    }

    // Log response details: status code, time, headers, body
    public static void logResponse(Response response) {
        ExtentTest test = ExtentTestNGListener.getTest();
        if (test == null || response == null) return;

        // Log metadata as plain text
        StringBuilder meta = new StringBuilder();
        meta.append("Response Status: ").append(response.getStatusCode()).append("\n");
        meta.append("Response Time (ms): ").append(response.getTime()).append("\n");

        // Log response headers
        try {
            if (response.getHeaders() != null) {
                meta.append("Response Headers:\n");
                response.getHeaders().asList().forEach(h -> meta.append(h.getName()).append(": ").append(h.getValue()).append("\n"));
            }
        } catch (Exception ignored) {
            // ignore header extraction issues
        }

        test.info(meta.toString());

        // Log body as pure JSON if possible, otherwise as plain text block
        String rawBody = response.getBody() != null ? response.getBody().asString() : "";
        String bodyStr = toJsonString(rawBody);
        test.info(MarkupHelper.createCodeBlock(bodyStr, CodeLanguage.JSON));
    }

    // Convert object or JSON string to a JSON string; if not JSON, return its toString
    private static String toJsonString(Object obj) {
        if (obj == null) return "";
        String s = obj instanceof String ? (String) obj : obj.toString();
        String trimmed = s.trim();
        // crude JSON detection
        if ((trimmed.startsWith("{") && trimmed.endsWith("}")) || (trimmed.startsWith("[") && trimmed.endsWith("]"))) {
            return s;
        }
        // not JSON — escape and return as plain text inside code block
        return s;
    }
}