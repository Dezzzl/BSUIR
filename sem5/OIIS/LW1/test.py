import matplotlib.pyplot as plt
import numpy as np

plt.style.use('seaborn-poster')


def FFT(x):
    """
    A recursive implementation of
    the 1D Cooley-Tukey FFT, the
    input should have a length of
    power of 2.
    """
    N = len(x)

    if N == 1:
        return x
    else:
        X_even = FFT(x[::2])
        X_odd = FFT(x[1::2])
        factor = np.exp(-2j * np.pi * np.arange(N) / N)

        X = np.concatenate(
            [X_even + factor[:int(N / 2)] * X_odd,
             X_even + factor[int(N / 2):] * X_odd])
        return X


# sampling rate
sr = 128
# sampling interval
ts = 1.0 / sr
t = np.arange(0, 1, ts)

freq = 1.
x = 3 * np.sin(2 * np.pi * freq * t)

freq = 4
x += np.sin(2 * np.pi * freq * t)

freq = 7
x += 0.5 * np.sin(2 * np.pi * freq * t)

plt.figure(figsize=(8, 6))
plt.plot(t, x, 'r')
plt.ylabel('Amplitude')

plt.show()