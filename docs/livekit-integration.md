# LiveKit integration contract

Good Meet owns meeting workflows, participant authorization, room naming, and short-lived join credentials. LiveKit Cloud owns rooms, participants, WebRTC signaling, SFU media transport, and recording/egress.

The runtime paths are deliberately separate:

```text
Client -> API Gateway -> Good Meet REST/WebSocket -> PostgreSQL/Redis
Client -> LiveKit SDK -> LiveKit Cloud
Good Meet -> LiveKit Server API
LiveKit Cloud -> Good Meet webhook endpoint
```

## Required LiveKit configuration

Configure a LiveKit project and provide these environment variables to Good Meet:

```text
LIVEKIT_URL
LIVEKIT_API_KEY
LIVEKIT_API_SECRET
```

The backend must verify the meeting lifecycle and participant role before issuing a LiveKit access token. Tokens must be short-lived and contain only server-derived room and participant grants. Never accept host or moderator grants directly from the client.

The frontend receives the URL and token from Good Meet, then connects directly to LiveKit using the appropriate LiveKit client SDK. The backend must not proxy WebRTC media.

Webhook requests must be verified with the LiveKit API secret before they update meeting or participant state. Handlers must be idempotent because webhook delivery can be retried.

## Local infrastructure

`compose.yaml` starts PostgreSQL and Redis for backend development:

```powershell
docker compose up -d
```

LiveKit Cloud is external to the backend compose stack. A local LiveKit server may be configured separately through the same `LIVEKIT_*` variables when offline development is required.
