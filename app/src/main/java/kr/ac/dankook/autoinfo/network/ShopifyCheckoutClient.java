package kr.ac.dankook.autoinfo.network;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ShopifyCheckoutClient {


    private static final String SHOP_DOMAIN = "gi1598-gr.myshopify.com";
    private static final String API_VERSION = "2025-10";
    private static final String STOREFRONT_TOKEN = "";
    private static final String ENDPOINT =
            "https://" + SHOP_DOMAIN + "/api/" + API_VERSION + "/graphql.json";

    public interface ResultCallback {
        void onSuccess(@NonNull String checkoutUrl);
        void onError(@NonNull String message);
    }

    public static void createCheckout(
            @NonNull String variantGid,
            int quantity,
            String customerEmail,
            String customerName,
            @NonNull ResultCallback cb
    ) {
        try {
            // 1) GraphQL mutation
            String mutation =
                    "mutation checkoutCreate($input: CheckoutCreateInput!) {"
                            + " checkoutCreate(input: $input) {"
                            + "   checkout { webUrl id }"
                            + "   checkoutUserErrors { field message }"
                            + " }"
                            + "}";

            JSONObject input = new JSONObject();

            JSONArray lineItems = new JSONArray();
            JSONObject item = new JSONObject();
            item.put("variantId", variantGid);
            item.put("quantity", quantity);
            lineItems.put(item);
            input.put("lineItems", lineItems);

            if (customerEmail != null && !customerEmail.isEmpty()) {
                input.put("email", customerEmail);
            }

            if (customerName != null && !customerName.isEmpty()) {
                String[] parts = customerName.trim().split(" ");
                String firstName = parts.length > 0 ? parts[0] : "";
                String lastName = (parts.length > 1) ? customerName.replace(firstName, "").trim() : ".";
                if (lastName.isEmpty()) lastName = ".";

                JSONObject shippingAddress = new JSONObject();
                shippingAddress.put("firstName", firstName);
                shippingAddress.put("lastName", lastName);
                input.put("shippingAddress", shippingAddress);
            }

            JSONObject variables = new JSONObject();
            variables.put("input", input);

            JSONObject bodyJson = new JSONObject();
            bodyJson.put("query", mutation);
            bodyJson.put("variables", variables);

            RequestBody body = RequestBody.create(
                    bodyJson.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request req = new Request.Builder()
                    .url(ENDPOINT)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Shopify-Storefront-Access-Token", STOREFRONT_TOKEN)
                    .post(body)
                    .build();

            HttpClient.get().newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    cb.onError("네트워크 오류: " + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String resp = response.body() != null ? response.body().string() : "";
                    if (!response.isSuccessful()) {
                        cb.onError("Shopify 오류(" + response.code() + "): " + resp);
                        return;
                    }

                    try {
                        JSONObject json = new JSONObject(resp);

                        // GraphQL errors
                        if (json.has("errors")) {
                            cb.onError("GraphQL errors: " + json.getJSONArray("errors").toString());
                            return;
                        }

                        JSONObject checkoutCreate = json.getJSONObject("data")
                                .getJSONObject("checkoutCreate");

                        JSONArray userErrors = checkoutCreate.getJSONArray("checkoutUserErrors");
                        if (userErrors.length() > 0) {
                            cb.onError("checkoutUserErrors: " + userErrors.toString());
                            return;
                        }

                        String checkoutUrl = checkoutCreate.getJSONObject("checkout")
                                .getString("webUrl");

                        cb.onSuccess(checkoutUrl);

                    } catch (Exception e) {
                        cb.onError("응답 파싱 오류: " + e.getMessage() + "\nresp=" + resp);
                    }
                }
            });

        } catch (Exception e) {
            cb.onError("요청 생성 오류: " + e.getMessage());
        }
    }
}
