export interface PostPayload {
    body: {
        text: string
        image?: {
            id: number
        }
        video?: {
            id: number
        }
    };
}