# KizzyRPC API Documentation

This document provides instructions on how to use the KizzyRPC API to manage your Discord Rich Presence from external applications.

## Enabling the API

1.  Open the KizzyRPC app.
2.  Go to **Settings > RPC Settings**.
3.  Tap on **API Settings**.
4.  Enable the **Enable API Server** switch.

## API Endpoint

*   **URL:** `http://<your-device-ip>:8080/rpc`
*   **Method:** `POST`
*   **Headers:**
    *   `X-Api-Key`: Your API key. You can find this in the API Settings screen.
*   **Body:** A JSON object representing the `Presence` you want to set.

## `Presence` Object Structure

```json
{
  "activities": [
    {
      "name": "string",
      "state": "string",
      "details": "string",
      "party": {
        "id": "string",
        "size": [
          -2147483648,
          2147483647
        ]
      },
      "type": 0,
      "platform": "string",
      "timestamps": {
        "start": 0,
        "end": 0
      },
      "assets": {
        "large_image": "string",
        "large_text": "string",
        "small_image": "string",
        "small_text": "string"
      },
      "buttons": [
        "string"
      ],
      "metadata": {
        "button_urls": [
          "string"
        ]
      },
      "application_id": "string",
      "url": "string"
    }
  ],
  "afk": true,
  "since": 0,
  "status": "online"
}
```

## Example Request (using cURL)

```bash
curl -X POST \
  http://<your-device-ip>:8080/rpc \
  -H 'X-Api-Key: <your-api-key>' \
  -H 'Content-Type: application/json' \
  -d '{
    "activities": [
      {
        "name": "My Awesome Game",
        "details": "Level 10",
        "state": "In a heated battle!"
      }
    ]
  }'
```

## Security

The API uses a randomly generated API key for authentication. This key is stored securely on your device and is required for all API requests. Please keep your API key confidential.
