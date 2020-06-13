package br.com.fiap.codigoaltaperformance.tabalho02.atendimento;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner teclado = new Scanner(System.in);
		FilaEncadeada filaAtendimento = new FilaEncadeada();
		FilaEncadeada filaInternacao = new FilaEncadeada();
		List<paciente> Lpacientes = new ArrayList<>();
		paciente Lpaciente = new paciente();
		long[] leitoInternacao = null;
		paciente p;
		filaAtendimento.init();
		filaInternacao.init();
		int opcao;

		String nome;
		long cpf;

		// rotina para informar a quantidade de leitos
		do {
			System.out.println("Informe a quantidade de leitos");
			opcao = teclado.nextInt();

		} while (opcao < 0);
		leitoInternacao = new long[opcao];

		opcao = 0;
		do {
			System.out.print("|------------------------------------------------------|\n");
			System.out.print("| Opção 0 - Sair                                       |\n");
			System.out.print("| Opção 1 - Colocar paciente na Fila                   |\n");
			System.out.print("| Opção 2 - Atender paciente                           |\n");
			System.out.print("| Opção 3 - Aplicar alta paciente                      |\n");
			System.out.print("| Opção 4 - Aplicar óbito paciente                     |\n");
			System.out.print("| Opção 5 - Listar fila de atendimento                 |\n");
			System.out.print("| Opção 6 - Listar fila de internação                  |\n");
			System.out.print("| Opção 7 - Informações do paciente através do CPF     |\n");
			System.out.print("|------------------------------------------------------|\n");
			System.out.print("Digite uma opção: ");
			opcao = teclado.nextInt();
			switch (opcao) {
			case 1:
				p = new paciente();

				System.out.print("Informe o nome do paciente:");
				nome = teclado.next();
				p.setNome(nome);

				System.out.print("Informe o cpf:");
				cpf = teclado.nextLong();
				p.setCpf(cpf);

				p.setStatus("filaAtendimento");

				filaAtendimento.enqueue(p);
				Lpacientes.add(p);

				break;
			case 2:
				System.out.println("Atender paciente da fila");
				if (!filaAtendimento.isEmpty()) {
					p = filaAtendimento.dequeue();
					int sintoma = 0;
					int risco = 0;
					String resposta = "N";
					System.out.println("O paciente possui sintomas? (S/[N])");
					resposta = teclado.next();
					if (resposta.equalsIgnoreCase("S")) {
						do {
							System.out.println("Informe o sintoma:\n" + ListSintomas(Estatico.sintomas));
							sintoma = teclado.nextInt();

							if (sintoma == 1) {
								risco = risco + 5;
							}
							if (sintoma == 2) {
								risco = risco + 1;
							}
							if (sintoma == 3) {
								risco = risco + 1;
							}
							if (sintoma == 4) {
								risco = risco + 1;
							}
							if (sintoma == 5) {
								risco = risco + 3;
							}
							if (sintoma == 6) {
								risco = risco + 10;
							}
							if (sintoma == 7) {
								risco = risco + 1;
							}
							if (sintoma == 8) {
								risco = risco + 1;
							}
							if (sintoma == 9) {
								risco = risco + 3;
							}
							if (sintoma == 10) {
								risco = risco + 10;
							}

							if (sintoma != 0)
								p.getSintoma().enqueue(Estatico.sintomas[sintoma]);

							System.out.println("Deseja listar os sintomas do paciente? (S/[N])");
							resposta = teclado.next();
							if (resposta.equalsIgnoreCase("S")) {
								p.getSintoma().mostrar();
							}

							System.out.println("Deseja informar mais sintomas? (S/[N])");
							resposta = teclado.next();

						} while (sintoma == 0 || resposta.equalsIgnoreCase("S"));
					}

					if (risco >= 10) {
						if (!p.getSintoma().isEmpty()) {
							boolean havagas = false;
							for (int i = 0; i < leitoInternacao.length; i++) {
								if (leitoInternacao[i] == 0) {
									leitoInternacao[i] = p.getCpf();
									havagas = true;
									p.setStatus("Internado.");
									System.out.println("Internando o paciente " + p + " no leito " + i);
									break;
								}
							}

							if (!havagas) {
								p.setStatus("filaInternação.");
								filaInternacao.enqueue(p);
								System.out.println(
										"Não há leitos vagos. Inserindo paciente " + p + " na lista de internação");

							}
						} else {
							p.setStatus("Liberado.");
							System.out.println("Paciente: " + p + ". Será liberado");
						}
					} else {
						p.setStatus("Liberado.");
						System.out.println("Paciente: " + p + ". Será liberado");
					}
				} else {
					System.out.println("Não tem pacientes na fila");

				}

				break;
			case 3:
				System.out.println("Informe o CPF que deseja aplicar a alta: ");
				cpf = teclado.nextLong();
				boolean encontrou = false;

				for (int i = 0; i < leitoInternacao.length; i++) {
					if (leitoInternacao[i] == cpf) {
						leitoInternacao[i] = 0;
						System.out.println("Paciente: " + cpf + ". Recebeu alta. Leito " + i + " foi desoculpado.");

						for (int j = 0; j < Lpacientes.size(); j++) {
							if (Lpacientes.get(j).getCpf() == cpf) {
								Lpacientes.get(j).setStatus("Em alta");
							}
						}

						if (!filaInternacao.isEmpty()) {
							p = filaInternacao.dequeue();
							leitoInternacao[i] = p.getCpf();
							System.out.println(
									"Paciente " + p + " que esta na fila de internação foi internado no leito: " + i);
						}
						encontrou = true;
						break;
					}
					;

				}
				if (!encontrou) {
					System.out.println("Não encontrou o paciente: " + cpf);
				}

				break;

				case 4:
				System.out.println("Informe o CPF que deseja aplicar óbito: ");
				cpf = teclado.nextLong();
				encontrou = false;


				for (int i = 0; i < leitoInternacao.length; i++) {
					if (leitoInternacao[i] == cpf) {
						leitoInternacao[i] = 0;
						System.out.println("Paciente: " + cpf + ". foi a óbito. Leito " + i + " foi desoculpado.");

						for (int j = 0; j < Lpacientes.size(); j++) {
							if (Lpacientes.get(j).getCpf() == cpf) {
								Lpacientes.get(j).setStatus("Óbito");
							}
						}

						if (!filaInternacao.isEmpty()) {
							p = filaInternacao.dequeue();
							leitoInternacao[i] = p.getCpf();
							System.out.println(
									"Paciente " + p + " que esta na fila de internação foi a óbito no leito: " + i);
						}
						encontrou = true;
						break;
					}
					;

				}
				if (!encontrou) {
					System.out.println("Não encontrou o paciente: " + cpf);
				}

				break;

			case 5:
				System.out.println("Listando fila de Atendimento");
				filaAtendimento.mostrar();
				System.out.println();
				break;
			case 6:
				System.out.println("Listando fila de Internação");
				filaInternacao.mostrar();
				System.out.println();
				break;
			case 7:
				System.out.println("Informe o CPF que deseja consultar as informações: ");
				cpf = teclado.nextLong();
				boolean encontrouPaciente = false;

				System.out.println("Lista de pacientes");
				for (int i = 0; i < Lpacientes.size(); i++) {
					if (Lpacientes.get(i).getCpf() == cpf) {
						System.out.println("Nome: " + Lpacientes.get(i).getNome() + " - " + "CPF: " + Lpacientes.get(i).getCpf() + " - " + "Status: " + Lpacientes.get(i).getStatus());
						encontrouPaciente = true;
					}
				}

				if (!encontrouPaciente) {
					System.out.println("Paciente não encontrado");
				}
			break;
			default:
			case 0:
				if (!filaAtendimento.isEmpty()) {
					System.out.println("Há pacientes na fila, deseja listá-los");
					while (!filaAtendimento.isEmpty()) {
						System.out.println(filaAtendimento.dequeue());
					}

				}
				opcao = 0;
			}
		} while (opcao > 0);

		teclado.close();
	}

	private static String ListSintomas(String[] sintomas) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sintomas.length; i++) {
			sb.append(i + " - " + sintomas[i] + "\n");

		}
		return sb.toString();
	}

}
