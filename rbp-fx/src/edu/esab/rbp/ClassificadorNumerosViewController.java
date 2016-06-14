/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esab.rbp;

import edu.esab.rbp.mnist.ClassificadorMnist1024x128xSomador;
import edu.esab.rbp.mnist.ImagemMnist;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author helio.rodrigues
 */
public class ClassificadorNumerosViewController implements Initializable {

    private ClassificadorMnist1024x128xSomador classificadorMnist = null;

    int[] acertos = new int[10];
    int[] erros = new int[10];
    int[] qtdes = new int[10];
    int[] reconhecimentosCorretos = new int[10];
    int[] reconhecimentosErrados = new int[10];
    int totalAcertos = 0;
    int totalErros = 0;
    int totalReconhecimentosCorretos = 0;
    int totalReconhecimentosErrados = 0;
    private final List<ImagemMnist> imagensVerificacao = new ArrayList<>();
    private int indiceImagemMnist = 0;
    private Thread threadVerificacao;

    @FXML
    private TextArea imagemMnistTextArea;

    @FXML
    private Label taxaAcertoGeralLabel;

    @FXML
    private Label desempenho0Label;

    @FXML
    private Label desempenho1Label;

    @FXML
    private Label desempenho2Label;

    @FXML
    private Label desempenho3Label;

    @FXML
    private Label desempenho4Label;

    @FXML
    private Label desempenho5Label;

    @FXML
    private Label desempenho6Label;

    @FXML
    private Label desempenho7Label;

    @FXML
    private Label desempenho8Label;

    @FXML
    private Label desempenho9Label;

    @FXML
    private Label statusTreinamentoLabel;

    @FXML
    private Label statusVerificacaoLabel;

    @FXML
    private CheckBox numero0CheckBox;

    @FXML
    private CheckBox numero1CheckBox;

    @FXML
    private CheckBox numero2CheckBox;

    @FXML
    private CheckBox numero3CheckBox;

    @FXML
    private CheckBox numero4CheckBox;

    @FXML
    private CheckBox numero5CheckBox;

    @FXML
    private CheckBox numero6CheckBox;

    @FXML
    private CheckBox numero7CheckBox;

    @FXML
    private CheckBox numero8CheckBox;

    @FXML
    private CheckBox numero9CheckBox;

    @FXML
    private TextField rotuloImagemTextField;

    @FXML
    private Slider numeroImagensTreinamentoSlider;

    @FXML
    private Slider numeroImagensValidacaoSlider;

    @FXML
    private ProgressBar treinamentoProgressBar;

    @FXML
    private ProgressBar validacaoProgressBar;

    /**
     *
     * @param event
     */
    @FXML
    public void verificarAction(ActionEvent event) {
        statusVerificacaoLabel.setText("Status: Verificando, aguarde...");

        threadVerificacao = new Thread(new Runnable() {
            @Override
            public void run() {
                try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-images.idx3-ubyte");
                        InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/t10k-labels.idx1-ubyte")) {

                    DataInputStream imagemDataInput = new DataInputStream(imagemStream);
                    DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

                    rotuloDataInput.readInt(); // Numero magico.
                    rotuloDataInput.readInt(); // Numero de rotulos.

                    imagemDataInput.readInt(); // Numero magico.
                    imagemDataInput.readInt(); // Numero de imagens.
                    imagemDataInput.readInt(); // Largura das imagens.
                    imagemDataInput.readInt(); // Altura das imagens.

                    // Testa a rede.
                    totalAcertos = 0;
                    totalErros = 0;
                    indiceImagemMnist = 0;
                    imagensVerificacao.clear();

                    for (int i = 0; i < (int) numeroImagensValidacaoSlider.getValue(); i++) {
                        ImagemMnist imagemMnist = new ImagemMnist();
                        imagemMnist.carregar(imagemStream, rotuloStream);
                        imagensVerificacao.add(imagemMnist);

                        int classificacao = classificadorMnist.classificar(imagemMnist, true);
                        qtdes[imagemMnist.getRotulo()]++;

                        // Taxa de reconhecimento.
                        if (classificacao == imagemMnist.getRotulo()) {
                            totalAcertos++;
                            acertos[imagemMnist.getRotulo()]++;
                        } else {
                            totalErros++;
                            erros[imagemMnist.getRotulo()]++;
                        }

                        atualizarVerificacaoProgressBar(i, numeroImagensValidacaoSlider.getValue());
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            statusVerificacaoLabel.setText("Status: Concluído.");
                            taxaAcertoGeralLabel.setText("Taxa de Acerto Geral: " + (totalAcertos * 100) / (totalAcertos + totalErros) + "%");

                            desempenho0Label.setText("[0] -> Taxa de Acerto: " + ((acertos[0] * 100) / (acertos[0] + erros[0])));
                            desempenho1Label.setText("[1] -> Taxa de Acerto: " + ((acertos[1] * 100) / (acertos[1] + erros[1])));
                            desempenho2Label.setText("[2] -> Taxa de Acerto: " + ((acertos[2] * 100) / (acertos[2] + erros[2])));
                            desempenho3Label.setText("[3] -> Taxa de Acerto: " + ((acertos[3] * 100) / (acertos[3] + erros[3])));
                            desempenho4Label.setText("[4] -> Taxa de Acerto: " + ((acertos[4] * 100) / (acertos[4] + erros[4])));
                            desempenho5Label.setText("[5] -> Taxa de Acerto: " + ((acertos[5] * 100) / (acertos[5] + erros[5])));
                            desempenho6Label.setText("[6] -> Taxa de Acerto: " + ((acertos[6] * 100) / (acertos[6] + erros[6])));
                            desempenho7Label.setText("[7] -> Taxa de Acerto: " + ((acertos[7] * 100) / (acertos[7] + erros[7])));
                            desempenho8Label.setText("[8] -> Taxa de Acerto: " + ((acertos[8] * 100) / (acertos[8] + erros[8])));
                            desempenho9Label.setText("[9] -> Taxa de Acerto: " + ((acertos[9] * 100) / (acertos[9] + erros[9])));
                        }
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ClassificadorNumerosViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        threadVerificacao.setDaemon(true);
        threadVerificacao.start();
    }

    /**
     *
     * @param indice
     * @param total
     */
    private void atualizarTreinamentoProgressBar(final double indice, final double total) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                treinamentoProgressBar.setProgress(indice / total);
            }
        });
    }

    /**
     *
     * @param indice
     * @param total
     */
    private void atualizarVerificacaoProgressBar(final double indice, final double total) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                validacaoProgressBar.setProgress(indice / total);
            }
        });
    }

    @FXML
    public void treinarAction(ActionEvent event) {
        statusTreinamentoLabel.setText("Status: Treinando, aguarde...");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                classificadorMnist = new ClassificadorMnist1024x128xSomador();

                try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-images.idx3-ubyte");
                        InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-labels.idx1-ubyte")) {

                    DataInputStream imagemDataInput = new DataInputStream(imagemStream);
                    DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

                    rotuloDataInput.readInt(); // Numero magico.
                    rotuloDataInput.readInt(); // Numero de rotulos.

                    imagemDataInput.readInt(); // Numero magico.
                    imagemDataInput.readInt(); // Numero de imagens.
                    imagemDataInput.readInt(); // Largura das imagens.
                    imagemDataInput.readInt(); // Altura das imagens.

                    // Treina a rede.
                    int qtde0s = 0;
                    int qtde1s = 0;
                    ImagemMnist imagemMnist = new ImagemMnist();

                    for (int i = 0; i < (int) numeroImagensTreinamentoSlider.getValue(); i++) {
                        imagemMnist.carregar(imagemStream, rotuloStream);

                        if (imagemMnist.getRotulo() == 0 && qtde0s++ > 12) {
                            classificadorMnist.aprender(imagemMnist);
                        } else if (imagemMnist.getRotulo() == 1 && qtde1s++ > 3) {
                            classificadorMnist.aprender(imagemMnist);
                        } else if (imagemMnist.getRotulo() != 0 && imagemMnist.getRotulo() != 1) {
                            classificadorMnist.aprender(imagemMnist);
                        }

                        atualizarTreinamentoProgressBar(i, numeroImagensTreinamentoSlider.getValue());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ClassificadorNumerosViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Calibrar.
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusTreinamentoLabel.setText("Status: Calibrando, aguarde...");
                    }
                });

                try (InputStream imagemStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-images.idx3-ubyte");
                        InputStream rotuloStream = getClass().getResourceAsStream("/com/lecun/yann/mnist/train-labels.idx1-ubyte")) {

                    DataInputStream imagemDataInput = new DataInputStream(imagemStream);
                    DataInputStream rotuloDataInput = new DataInputStream(rotuloStream);

                    rotuloDataInput.readInt(); // Numero magico.
                    rotuloDataInput.readInt(); // Numero de rotulos.

                    imagemDataInput.readInt(); // Numero magico.
                    imagemDataInput.readInt(); // Numero de imagens.
                    imagemDataInput.readInt(); // Largura das imagens.
                    imagemDataInput.readInt(); // Altura das imagens.

                    // Treina a rede.
                    ImagemMnist imagemMnist = new ImagemMnist();

                    for (int i = 0; i < (int) numeroImagensTreinamentoSlider.getValue(); i++) {
                        imagemMnist.carregar(imagemStream, rotuloStream);
                        classificadorMnist.calibrar(imagemMnist, true);
                        atualizarTreinamentoProgressBar(i, numeroImagensTreinamentoSlider.getValue());
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            statusTreinamentoLabel.setText("Status: Concluído.");
                        }
                    });
                } catch (IOException ex) {
                    Logger.getLogger(ClassificadorNumerosViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        thread.setDaemon(true);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    /**
     *
     * @param event
     */
    @FXML
    public void proximaImagemAction(ActionEvent event) {
        // Define a imagem.
        indiceImagemMnist++;

        if (indiceImagemMnist >= imagensVerificacao.size()) {
            indiceImagemMnist = imagensVerificacao.size() - 1;
        }

        int[][] imagem = imagensVerificacao.get(indiceImagemMnist).getImagem();
        String string = "";

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                string += (imagem[i][j] == 1) ? " @" : " .";
            }
            string += "\n";
        }

        imagemMnistTextArea.setText(string);
        rotuloImagemTextField.setText(imagensVerificacao.get(indiceImagemMnist).getRotulo() + "");

        // Classifica a imagem.
        // int[] classificacao = classificadorMnistVarredura.classificar(imagensVerificacao.get(indiceImagemMnist));
        int classificacao = classificadorMnist.classificar(imagensVerificacao.get(indiceImagemMnist), true);

        numero0CheckBox.setSelected(classificacao == 0);
        numero1CheckBox.setSelected(classificacao == 1);
        numero2CheckBox.setSelected(classificacao == 2);
        numero3CheckBox.setSelected(classificacao == 3);
        numero4CheckBox.setSelected(classificacao == 4);
        numero5CheckBox.setSelected(classificacao == 5);
        numero6CheckBox.setSelected(classificacao == 6);
        numero7CheckBox.setSelected(classificacao == 7);
        numero8CheckBox.setSelected(classificacao == 8);
        numero9CheckBox.setSelected(classificacao == 9);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void imagemAnteriorAction(ActionEvent event) {
        // Define a imagem.
        indiceImagemMnist--;

        if (indiceImagemMnist < 0) {
            indiceImagemMnist = 0;
        }

        int[][] imagem = imagensVerificacao.get(indiceImagemMnist).getImagem();
        String string = "";

        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                string += (imagem[i][j] == 1) ? " @" : " .";
            }
            string += "\n";
        }

        imagemMnistTextArea.setText(string);
        rotuloImagemTextField.setText(imagensVerificacao.get(indiceImagemMnist).getRotulo() + "");

        // Classifica a imagem.
        int classificacao = classificadorMnist.classificar(imagensVerificacao.get(indiceImagemMnist), true);

        numero0CheckBox.setSelected(classificacao == 0);
        numero1CheckBox.setSelected(classificacao == 1);
        numero2CheckBox.setSelected(classificacao == 2);
        numero3CheckBox.setSelected(classificacao == 3);
        numero4CheckBox.setSelected(classificacao == 4);
        numero5CheckBox.setSelected(classificacao == 5);
        numero6CheckBox.setSelected(classificacao == 6);
        numero7CheckBox.setSelected(classificacao == 7);
        numero8CheckBox.setSelected(classificacao == 8);
        numero9CheckBox.setSelected(classificacao == 9);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
