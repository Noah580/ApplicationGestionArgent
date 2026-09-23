import { LitElement, html } from 'lit';

export class AppRoot extends LitElement {
  render() {
    return html`<h1>Gestion d'argent</h1>`;
  }
}

customElements.define('app-root', AppRoot);
