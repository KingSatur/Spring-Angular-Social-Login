export class AuthState {
  token: string;
  google_id: string;
  first_name: string;
  last_name: string;
  photo_url: string;
  id: string;

  constructor(json) {
    this.token = json?.token;
    this.id = json?.id;
    this.google_id = json?.google_id;
    this.first_name = json?.first_name;
    this.last_name = json?.last_name;
    this.photo_url = json?.photo_url;
  }
}

export class AuthRequestDto {
  value: string;

  constructor(value) {
    this.value = value;
  }
}
