🗳️ Urna Cidadã — Simulador Educativo de Votação Eletrônica

Projeto de Extensão Curricular desenvolvido na disciplina de **Programação Orientada a Objetos (POO)** do curso de **Engenharia de Software** da **UNIJORGE** (Semestre 2026.2).

🎯 Objetivo
Promover a literacia digital e eleitoral por meio de um simulador educativo da Urna Eletrônica Brasileira, aliado a ações de conscientização sobre verificação de fatos e combate à desinformação (*fake news* e *deepfakes*).

👥 Equipe
* Esney Fiel de Jesus Damasceno
* Guilherme Calazans Lima
* Luis Gustavo Albuquerque
* Pedro Henrique dos Anjos

🏗️ Arquitetura e Conceitos de POO Aplicados
O sistema foi desenvolvido em **Java (Swing)** seguindo arquitetura em camadas e os quatro pilares da Programação Orientada a Objetos:

* **`model`**: Classes de domínio (`Candidato`, `CandidatoProporcional`, `CandidatoMajoritario` e `Voto`), aplicando **abstração**, **encapsulamento**, **herança** e **polimorfismo** na regra de exibição e contagem de votos.
* **`repository`**: Camada de persistência (`FirebaseRepository`) preparada para integração com o **Firebase Cloud Firestore** e fallback local em memória.
* **`service`**: Regras de negócio (`UrnaService`) responsáveis pelo controle de etapas da votação (Vereador e Prefeito), validação de dígitos e geração do Boletim de Urna.
* **`ui`**: Interface gráfica desktop (`UrnaFrame`) construída em Java Swing com teclado numérico, botões funcionais (`BRANCO`, `CORRIGE`, `CONFIRMA`), feedback sonoro e menu de apuração para o mesário.

🚀 Como Executar
1. Certifique-se de ter o **JDK 17+** e o **Apache Maven** instalados.
2. Clone este repositório:
   ```bash
   git clone [https://github.com/guifxp6/urnacidada.git](https://github.com/guifxp6/urnacidada.git)
