package server.stats;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Arrays;

import compute.Task;

public class DataStats implements Task<HashMap<String, Double>>, Serializable {
    private static final long serialVersionUID = 666L;

    // lag para autocorrelação
    private Integer lag;

    // Série temporal passada à classe
    private final Double[] data;

    public DataStats(Double[] data, Integer autocorrLag) {
        this.data = data;
        this.lag = autocorrLag;
    }

    // NOTA: as referências para as fórmulas são um material de apoio do prof. Gilberto Rinaldi.
    // Infelizmente, como não tenho autorização do mesmo, não posso fornecer os materiais.

    // média
    private Double mean() {
        double sum = 0.0;
        for (double x : this.data) {
            sum += x;
        }
        return sum / this.data.length;
    }

    // mediana
    private Double median() {
        Double[] sortedData = this.data.clone();
        Arrays.sort(sortedData);
        // com o array ordenado, é só calcular
        int mid = sortedData.length / 2;
        if (sortedData.length % 2 == 1) {
            return sortedData[mid];
        }
        return (sortedData[mid - 1] + sortedData[mid]) / 2.0f;
    }

    // variância
    private Double variance() {
        double mean = this.mean();
        double sum = 0.0;
        double d; // variável auxiliar para código limpo
        for (double x : this.data) {
            d = x - mean;
            sum += d * d;
        }
        return sum / this.data.length;
    }

    // desvio padrão
    private Double std() {
        return Math.sqrt(this.variance());
    }

    // coeficiente de variação
    private Double vc() {
        return (this.std() / this.mean()) * 100.0;
    }

    // coeficiente de assimetria
    private Double skewness() {
        return 3.0 * (this.mean() - this.median()) / this.std();
    }

    // coeficiente de autocorrelação linear
    private Double linearAutoCorrCoef() {
        // Correlação com ele mesmo é sempre 100%
        if(lag == 0) return 1.0;
        // Lag inválido
        if(lag < 0 || lag >= this.data.length) return 0.0;

        // Primeiro criamos as duas listas lagadas
        // x é a lista original, mas sem os últimos lag elementos
        Double[] x = Arrays.copyOfRange(this.data, 0, this.data.length - lag);
        // y é a lista original, mas sem os primeiros lag elementos
        Double[] y = Arrays.copyOfRange(this.data, lag, this.data.length);

        // Agora calculamos a correlação entre x e y
        // Preparando os valores
        int n = x.length;
        double sumX = 0.0, sumY = 0.0, sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
        for(int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
            sumY2 += y[i] * y[i];
        }

        // Coeficiente de correlação de Pearson
        return (n * sumXY - (sumX * sumY)) / Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));
    }

    private HashMap<String, Double> buildAnalysis() {
        HashMap<String, Double> res = new HashMap<>();
        res.put("mean", mean());
        res.put("median", median());
        res.put("variance", variance());
        res.put("stdDev", std());
        res.put("lacorrcoef", linearAutoCorrCoef());
        res.put("vc", vc());
        res.put("skewness", skewness());
        return res;
    }

    public HashMap<String, Double> execute() {
        return buildAnalysis();
    }
}
