export interface LoginResponse {
  access_token: string,
  expires_in: number,
}

export interface LoginRequest {
  username: string,
  password: string,
}
