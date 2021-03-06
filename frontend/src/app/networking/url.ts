import axios, {AxiosError, AxiosRequestConfig, AxiosResponse} from "axios";

interface RefreshTokenResponse {
  access_token: string,
  expires_in: number
}

export default class Network<T> {
  constructor(private shouldRefresh: boolean = true) {
    this.get.bind(this)
    this.post.bind(this)
    this.startFunction.bind(this)
    this.refreshToken.bind(this)
  }

  async get(url: string, config: AxiosRequestConfig): Promise<AxiosResponse<T>> {
    const result = async () => {
      return await axios.get<T>(getFullUrl(url), config)
    }
    return await this.startFunction(result)
  }

  async post<U>(url: string, data: U, config?: AxiosRequestConfig | undefined): Promise<AxiosResponse<T>> {
    const result = async () => {
      return await axios.post<T>(getFullUrl(url), data, config)
    }
    return await this.startFunction(result)
  }

  async startFunction(request: () => Promise<AxiosResponse<T>>): Promise<AxiosResponse<T>> {
    const result = await request()

    switch (result.status) {
      case 401:
      case 403:
        if (!this.shouldRefresh) return result
        await this.refreshToken()
        return await request()
      default:
        return result
    }
  }

  async refreshToken() {
    const token = localStorage.getItem('token')
    if (!token) return;

    const result = await axios.post<RefreshTokenResponse>("/auth/refresh", {}, {
      headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' }
    })

    if (result.status !== 200) return

    localStorage.setItem('token', result.data.access_token)
  }
}

export const getFullUrl: (url: string) => string = (url) => {
  const prefix = process.env.NODE_ENV === 'development' ? 'http://localhost:8080' : ""
  return `${prefix}${url}`
}
