import numpy as np
import matplotlib.pyplot as plt


def create_fun(fun: str, N: int, num_of_points, freq_of_discretisation):
    T = 1.0 / freq_of_discretisation
    x = np.linspace(0.0, num_of_points * T, num_of_points, endpoint=False)
    if (fun == "sin"):
        return np.sin(N * 2.0 * np.pi * x)
    else:
        return np.cos(N * 2.0 * np.pi * x)


def plot_signal(signal: np.ndarray, freq_of_discretisation: int):
    num_of_points = len(signal)
    T = 1.0 / freq_of_discretisation
    x = np.linspace(0.0, num_of_points * T, num_of_points, endpoint=False)

    plt.figure(figsize=(10, 4))
    plt.plot(x, signal)
    plt.title('Сигнал во временной области')
    plt.xlabel('Время [с]')
    plt.ylabel('Амплитуда')
    plt.grid()
    plt.show()


def FFT(x):
    N = len(x)
    if N == 1:
        return x
    else:
        X_even = FFT(x[::2])
        X_odd = FFT(x[1::2])
        factor = np.exp(2j * np.pi * np.arange(N) / N)
        X = np.concatenate([X_even + factor[:int(N / 2)] * X_odd,
                            X_even + factor[int(N / 2):] * X_odd])
        return X


def compute_frequencies(N, freq_of_discretisation):
    freqs = np.arange(-N // 2, N // 2) * freq_of_discretisation / N
    return freqs


def plot_spectrum(signal: np.ndarray, freq_of_discretisation: int):
    N = len(signal)
    yf = FFT(signal)
    xf = compute_frequencies(N, freq_of_discretisation)

    yf_shifted = np.concatenate([yf[N // 2:], yf[:N // 2]])

    plt.figure(figsize=(10, 4))
    plt.plot(xf, np.abs(yf_shifted))
    plt.title('Спектр сигнала (модуль ДПФ)')
    plt.xlabel('Частота [Гц]')
    plt.ylabel('Амплитуда')
    plt.grid()
    plt.show()


if __name__ == '__main__':
    signal = create_fun("sin", 100, 1024, 1024)
    plot_signal(signal, 1024)
    plot_spectrum(signal, 1024)
